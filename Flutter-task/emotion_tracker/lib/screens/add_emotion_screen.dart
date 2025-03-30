import 'package:flutter/material.dart';

class AddEmotionScreen extends StatefulWidget {
  const AddEmotionScreen({super.key});

  @override
  _AddEmotionScreenState createState() => _AddEmotionScreenState();
}

class _AddEmotionScreenState extends State<AddEmotionScreen>
    with SingleTickerProviderStateMixin {
  final TextEditingController _emotionController = TextEditingController();
  String? _selectedEmoji; // No default emoji initially
  Color? _selectedColor; // No default color initially

  // ✅ Map of Emotion-Emoji Pairs
  final Map<String, String> _emotionEmojiMap = {
    'happy': '😄',
    'sad': '😢',
    'angry': '😡',
    'surprise': '😲',
    'love': '❤️',
    'excited': '🤩',
    'tired': '😴',
    'scared': '😨',
    'neutral': '😐',
  };

  // ✅ List of Available Emojis
  final List<String> _allEmojis = [
    '😄',
    '😢',
    '😡',
    '😲',
    '❤️',
    '🤩',
    '😴',
    '😨',
    '😐',
  ];

  @override
  void initState() {
    super.initState();
    // ✅ Listen to changes in emotion input and update emoji
    _emotionController.addListener(_updateEmoji);
  }

  @override
  void dispose() {
    _emotionController.dispose();
    super.dispose();
  }

  // ✅ Update Emoji based on Emotion Name
  void _updateEmoji() {
    String emotion = _emotionController.text.trim().toLowerCase();
    setState(() {
      _selectedEmoji = _emotionEmojiMap[emotion]; // Auto-assign emoji
    });
  }

  // ✅ Save Emotion with Emoji & Optional Color
  void _saveEmotion() {
    if (_emotionController.text.trim().isEmpty) {
      _showSnackbar('⚠️ Emotion cannot be empty!');
      return;
    }

    if (_selectedEmoji == null) {
      _showSnackbar(
        '⚠️ No matching emoji found! Please enter a valid emotion or select an emoji.',
      );
      return;
    }

    String emotion = _emotionController.text.trim().toLowerCase();

    // ✅ If no color selected, assign default color
    Color selectedColor = _selectedColor ?? Colors.grey;

    Navigator.pop(context, {
      'emotion': emotion,
      'emoji': _selectedEmoji,
      'color': selectedColor.value.toString(),
    });
  }

  // ✅ Show Snackbar for errors
  void _showSnackbar(String message) {
    ScaffoldMessenger.of(context).showSnackBar(
      SnackBar(
        content: Text(
          message,
          style: const TextStyle(fontSize: 16, fontWeight: FontWeight.bold),
        ),
        backgroundColor: Colors.redAccent,
        duration: const Duration(seconds: 2),
      ),
    );
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Add Custom Emotion'),
        backgroundColor: Colors.teal,
        elevation: 8,
      ),
      body: Container(
        padding: const EdgeInsets.symmetric(horizontal: 20.0, vertical: 30.0),
        decoration: const BoxDecoration(
          gradient: LinearGradient(
            begin: Alignment.topLeft,
            end: Alignment.bottomRight,
            colors: [Colors.deepPurple, Colors.teal],
          ),
        ),
        child: Center(
          child: SingleChildScrollView(
            child: Column(
              children: <Widget>[
                const Text(
                  '🎨 Create Your Emotion',
                  style: TextStyle(
                    fontSize: 28,
                    color: Colors.white,
                    fontWeight: FontWeight.bold,
                  ),
                ),
                const SizedBox(height: 20),
                TextField(
                  controller: _emotionController,
                  style: const TextStyle(fontSize: 20, color: Colors.black),
                  decoration: InputDecoration(
                    labelText: 'Type an Emotion',
                    labelStyle: const TextStyle(color: Colors.black54),
                    filled: true,
                    fillColor: Colors.white,
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(15.0),
                    ),
                  ),
                ),
                const SizedBox(height: 20),

                // ✅ Display Available Emojis Below the Text Box
                const Text(
                  'Or Choose an Emoji:',
                  style: TextStyle(fontSize: 18, color: Colors.white),
                ),
                const SizedBox(height: 10),
                Wrap(
                  spacing: 12,
                  runSpacing: 8,
                  children:
                      _allEmojis
                          .map(
                            (emoji) => GestureDetector(
                              onTap: () {
                                setState(() {
                                  _selectedEmoji = emoji;
                                });
                              },
                              child: Text(
                                emoji,
                                style: TextStyle(
                                  fontSize: 40,
                                  color:
                                      _selectedEmoji == emoji
                                          ? Colors.amber
                                          : Colors.white,
                                ),
                              ),
                            ),
                          )
                          .toList(),
                ),
                const SizedBox(height: 20),

                // ✅ Show Selected Emoji (if any)
                if (_selectedEmoji != null)
                  Column(
                    children: [
                      const Text(
                        'Selected Emoji:',
                        style: TextStyle(fontSize: 18, color: Colors.white),
                      ),
                      const SizedBox(height: 10),
                      Text(
                        _selectedEmoji!,
                        style: const TextStyle(
                          fontSize: 50,
                          color: Colors.amber,
                        ),
                      ),
                    ],
                  ),
                const SizedBox(height: 20),

                // ✅ Color Picker
                const Text(
                  'Pick a Color (Optional):',
                  style: TextStyle(fontSize: 18, color: Colors.white),
                ),
                const SizedBox(height: 10),
                Wrap(
                  spacing: 8,
                  children:
                      [
                            Colors.yellow,
                            Colors.blueAccent,
                            Colors.redAccent,
                            Colors.purpleAccent,
                            Colors.pinkAccent,
                            Colors.orangeAccent,
                            Colors.brown,
                            Colors.grey,
                          ]
                          .map(
                            (color) => GestureDetector(
                              onTap: () {
                                setState(() {
                                  _selectedColor = color;
                                });
                              },
                              child: CircleAvatar(
                                backgroundColor: color,
                                radius: 18,
                                child:
                                    _selectedColor == color
                                        ? const Icon(
                                          Icons.check,
                                          color: Colors.white,
                                        )
                                        : null,
                              ),
                            ),
                          )
                          .toList(),
                ),
                const SizedBox(height: 30),

                // ✅ Save Button
                ElevatedButton(
                  onPressed: _saveEmotion,
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.green,
                    padding: const EdgeInsets.symmetric(
                      horizontal: 50,
                      vertical: 15,
                    ),
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(25),
                    ),
                  ),
                  child: const Text(
                    '✅ Save Emotion',
                    style: TextStyle(
                      fontSize: 20,
                      fontWeight: FontWeight.bold,
                      color: Colors.white,
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
