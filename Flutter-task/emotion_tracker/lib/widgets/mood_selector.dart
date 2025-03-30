import 'package:flutter/material.dart';
import 'package:flutter/services.dart'; // For vibration feedback
import 'package:google_fonts/google_fonts.dart'; // Google Fonts for modern styling

class MoodSelector extends StatefulWidget {
  final Function(String) onMoodSelected;

  MoodSelector({required this.onMoodSelected});

  @override
  _MoodSelectorState createState() => _MoodSelectorState();
}

class _MoodSelectorState extends State<MoodSelector>
    with SingleTickerProviderStateMixin {
  String _selectedMood = ''; // Track selected mood
  double _iconSize = 50.0;

  void _selectMood(String mood) {
    setState(() {
      _selectedMood = mood;
    });

    // Vibrate for a short feedback
    HapticFeedback.lightImpact();

    // Pass the selected mood to parent widget
    widget.onMoodSelected(mood);
  }

  @override
  Widget build(BuildContext context) {
    return Column(
      mainAxisAlignment: MainAxisAlignment.center,
      children: <Widget>[
        Text(
          '😊 Select Your Mood',
          style: GoogleFonts.poppins(
            fontSize: 26,
            fontWeight: FontWeight.bold,
            color: Colors.teal,
          ),
        ),
        const SizedBox(height: 20),
        Row(
          mainAxisAlignment: MainAxisAlignment.spaceAround,
          children: <Widget>[
            _buildMoodIcon(
              'Happy',
              Icons.sentiment_very_satisfied,
              Colors.green,
            ),
            _buildMoodIcon('Sad', Icons.sentiment_dissatisfied, Colors.blue),
            _buildMoodIcon(
              'Angry',
              Icons.sentiment_very_dissatisfied,
              Colors.red,
            ),
            _buildMoodIcon(
              'Surprised',
              Icons.sentiment_very_satisfied_rounded,
              Colors.orange,
            ),
          ],
        ),
      ],
    );
  }

  Widget _buildMoodIcon(String mood, IconData icon, Color color) {
    bool isSelected = _selectedMood == mood;

    return GestureDetector(
      onTap: () {
        _selectMood(mood);
      },
      child: AnimatedContainer(
        duration: const Duration(milliseconds: 200),
        curve: Curves.easeInOut,
        padding: EdgeInsets.all(isSelected ? 15 : 10),
        decoration: BoxDecoration(
          color: isSelected ? color.withOpacity(0.2) : Colors.transparent,
          shape: BoxShape.circle,
          boxShadow:
              isSelected
                  ? [
                    BoxShadow(
                      color: color.withOpacity(0.5),
                      blurRadius: 10,
                      spreadRadius: 2,
                    ),
                  ]
                  : [],
        ),
        child: Icon(icon, color: color, size: isSelected ? 65 : _iconSize),
      ),
    );
  }
}
