import 'package:flutter/material.dart';
import 'localization.dart'; // Import localization logic
import 'add_emotion_screen.dart'; // Import AddEmotionScreen
import 'package:flutter_localizations/flutter_localizations.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  _HomeScreenState createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  String _emotion = ''; // Updated selected emotion
  String _selectedEmoji = ''; // Updated selected emoji
  Color _selectedColor = Colors.grey; // Updated selected color

  List<Map<String, dynamic>> emotions = [];
  int _emotionIndex = 0;
  Locale _selectedLocale = const Locale('en', 'US'); // 🌐 Default Language

  @override
  void initState() {
    super.initState();
    _addDefaultEmotions(); // Load default emotions
  }

  // ✅ Add default emotions
  void _addDefaultEmotions() {
    setState(() {
      emotions = [
        {
          'emotion': 'happy',
          'emoji': '😄',
          'color': Colors.yellow.shade700.value.toString(),
        },
        {
          'emotion': 'sad',
          'emoji': '😢',
          'color': Colors.blueAccent.value.toString(),
        },
        {
          'emotion': 'angry',
          'emoji': '😡',
          'color': Colors.redAccent.value.toString(),
        },
        {
          'emotion': 'surprised',
          'emoji': '😲',
          'color': Colors.purpleAccent.value.toString(),
        },
        {
          'emotion': 'love',
          'emoji': '❤️',
          'color': Colors.pinkAccent.value.toString(),
        },
        {
          'emotion': 'excited',
          'emoji': '🤩',
          'color': Colors.orangeAccent.value.toString(),
        },
      ];
    });
    print('✅ Default emotions loaded!');
  }

  // ✅ Cycle through emotions
  void _cycleEmotion() {
    if (emotions.isEmpty) {
      print('⚠️ No emotions available to cycle!');
      return;
    }

    setState(() {
      _emotionIndex = (_emotionIndex + 1) % emotions.length;
      _emotion = emotions[_emotionIndex]['emotion'];
      _selectedEmoji = emotions[_emotionIndex]['emoji'];
      _selectedColor = Color(
        int.parse(emotions[_emotionIndex]['color'] ?? '0xFF9E9E9E'),
      );
    });
  }

  // ✅ Reset emotion
  void _resetEmotion() {
    setState(() {
      _emotion = '';
      _selectedEmoji = '';
      _selectedColor = Colors.grey;
    });
  }

  // ✅ Navigate to AddEmotionScreen and get custom emotion
  Future<void> _navigateAndGetEmotion() async {
    Map<String, dynamic>? emotionData = await Navigator.push(
      context,
      MaterialPageRoute(builder: (context) => const AddEmotionScreen()),
    );

    if (emotionData != null) {
      String newEmotion = emotionData['emotion'];
      String emoji = emotionData['emoji'];
      String color = emotionData['color'];

      // ✅ Fix for color string conversion
      int colorValue = int.tryParse(color) ?? Colors.grey.value;
      setState(() {
        _emotion = newEmotion;
        _selectedEmoji = emoji;
        _selectedColor = Color(colorValue);
        emotions.add({'emotion': newEmotion, 'emoji': emoji, 'color': color});
      });

      print('✅ New emotion added: $newEmotion with emoji $emoji');
    }
  }

  // ✅ Change Language and Reload UI
  void _changeLanguage() {
    setState(() {
      _selectedLocale =
          _selectedLocale.languageCode == 'en'
              ? const Locale('es', 'ES')
              : const Locale('en', 'US');
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      locale: _selectedLocale,
      localizationsDelegates: const [
        AppLocalizations.delegate,
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
      ],
      supportedLocales: const [
        Locale('en', 'US'), // English
        Locale('es', 'ES'), // Spanish
      ],
      home: Scaffold(
        appBar: AppBar(
          title: Text(
            AppLocalizations.of(context)?.translate('emotion_tracker') ??
                'Emotion Tracker',
          ),
          centerTitle: true,
          backgroundColor: Colors.teal,
        ),
        body: _buildEmotionUI(AppLocalizations.of(context)),
      ),
    );
  }

  // ✅ UI Builder for Emotion Tracker
  Widget _buildEmotionUI(AppLocalizations? localizations) {
    return Center(
      child: Padding(
        padding: const EdgeInsets.all(25.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            Text(
              localizations?.translate('track_emotions') ??
                  'Track Your Emotions',
              style: const TextStyle(
                fontSize: 32,
                fontWeight: FontWeight.bold,
                color: Colors.black87,
              ),
              textAlign: TextAlign.center,
            ),
            const SizedBox(height: 25),

            /// Emotion Display Box
            _emotion.isNotEmpty
                ? _emotionWidget(_emotion, _selectedEmoji, _selectedColor)
                : _emptyEmotionWidget(localizations),

            const SizedBox(height: 30),
            _buildActionButton(
              localizations?.translate('cycle_emotion') ?? 'Cycle Emotion',
              _cycleEmotion,
              Colors.lightBlueAccent,
            ),
            const SizedBox(height: 15),
            _buildActionButton(
              localizations?.translate('reset_emotion') ?? 'Reset Emotion',
              _resetEmotion,
              Colors.redAccent,
            ),
            const SizedBox(height: 15),
            _buildActionButton(
              localizations?.translate('add_custom_emotion') ??
                  'Add Custom Emotion',
              _navigateAndGetEmotion,
              Colors.green,
            ),
            const SizedBox(height: 20),
            _buildLanguageButton(),
          ],
        ),
      ),
    );
  }

  /// 🌐 Language Toggle Button
  Widget _buildLanguageButton() {
    String currentLang =
        _selectedLocale.languageCode == 'en'
            ? '🇪🇸 Switch to Spanish'
            : '🇺🇸 Switch to English';

    return ElevatedButton(
      onPressed: _changeLanguage,
      style: ElevatedButton.styleFrom(
        backgroundColor: Colors.deepPurple,
        padding: const EdgeInsets.symmetric(horizontal: 40, vertical: 12),
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(25)),
      ),
      child: Text(
        currentLang,
        style: const TextStyle(
          fontSize: 18,
          color: Colors.white,
          fontWeight: FontWeight.w600,
        ),
      ),
    );
  }

  /// ✅ Widget to Show Current Emotion
  Widget _emotionWidget(String emotion, String emoji, Color color) {
    return Container(
      padding: const EdgeInsets.all(20),
      margin: const EdgeInsets.symmetric(vertical: 10),
      decoration: BoxDecoration(
        color: color,
        borderRadius: BorderRadius.circular(20),
        boxShadow: const [
          BoxShadow(color: Colors.black45, blurRadius: 8, offset: Offset(3, 5)),
        ],
      ),
      child: Column(
        children: [
          Text(emoji, style: const TextStyle(fontSize: 50)),
          const SizedBox(height: 10),
          Text(
            'Your emotion is: $emotion',
            style: const TextStyle(
              fontSize: 20,
              color: Colors.white,
              fontWeight: FontWeight.w600,
            ),
          ),
        ],
      ),
    );
  }

  /// 🆕 Default Widget when No Emotion is Selected
  Widget _emptyEmotionWidget(AppLocalizations? localizations) {
    return Container(
      padding: const EdgeInsets.all(20),
      decoration: BoxDecoration(
        color: Colors.grey.shade300,
        borderRadius: BorderRadius.circular(20),
        boxShadow: const [
          BoxShadow(color: Colors.black26, blurRadius: 6, offset: Offset(2, 4)),
        ],
      ),
      child: Text(
        localizations?.translate('no_emotion_selected') ??
            'No emotion selected!',
        style: const TextStyle(fontSize: 18, color: Colors.black54),
      ),
    );
  }

  /// ✅ Build Action Button
  Widget _buildActionButton(String text, VoidCallback onPressed, Color color) {
    return ElevatedButton(
      onPressed: onPressed,
      style: ElevatedButton.styleFrom(
        backgroundColor: color,
        padding: const EdgeInsets.symmetric(horizontal: 50, vertical: 15),
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(25)),
      ),
      child: Text(
        text,
        style: const TextStyle(
          fontSize: 18,
          color: Colors.white,
          fontWeight: FontWeight.w600,
        ),
      ),
    );
  }
}
