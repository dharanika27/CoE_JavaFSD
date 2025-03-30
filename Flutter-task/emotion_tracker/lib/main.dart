import 'package:emotion_tracker/firebase_options.dart'; // Ensure this file exists
import 'package:firebase_core/firebase_core.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:cloud_firestore/cloud_firestore.dart';
import 'screens/home_screen.dart'; // ✅ Corrected path for HomeScreen
import 'screens/localization.dart';
import 'package:flutter_localizations/flutter_localizations.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  try {
    // ✅ Initialize Firebase correctly for Web/Native platforms
    if (kIsWeb) {
      await Firebase.initializeApp(
        options: const FirebaseOptions(
          apiKey: "AIzaSyBPLMR_yIDMl2O6KjyUNypWFu-8nZha2b4",
          appId: "1:969781911627:web:32cf68bcefa5bc28542dce",
          messagingSenderId: "969781911627",
          projectId: "flutterbackend-b2ee2",
        ),
      );
    } else {
      await Firebase.initializeApp(
        options: DefaultFirebaseOptions.currentPlatform,
      );
    }

    print('✅ Firebase successfully initialized!');

    // ✅ Test Firestore Connection
    await _testFirestoreConnection();

    // Launch the app only after successful Firebase initialization
    runApp(const MyApp());
  } catch (e) {
    print('❌ Error initializing Firebase: $e');
    runApp(ErrorApp(errorMessage: e.toString()));
  }
}

// ✅ Test Firestore connection and handle connection errors
Future<void> _testFirestoreConnection() async {
  try {
    // Attempt Firestore write & read
    await FirebaseFirestore.instance.collection('test').doc('connection').set({
      'status': 'connected',
      'timestamp': DateTime.now().toString(),
    });

    DocumentSnapshot docSnapshot =
        await FirebaseFirestore.instance
            .collection('test')
            .doc('connection')
            .get();

    if (docSnapshot.exists) {
      print('✅ Firestore Connection Successful: ${docSnapshot.data()}');
    } else {
      throw Exception('⚠️ Firestore connection failed. No document found.');
    }
  } catch (e) {
    print('❌ Firestore Connection Error: $e');
    throw Exception('Firestore Connection Failed: $e');
  }
}

// ✅ Main Application Widget
class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Emotion Tracker',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(primarySwatch: Colors.teal),
      home: const HomeScreen(), // ✅ Ensure HomeScreen is correctly imported
      supportedLocales: const [
        Locale('en', 'US'), // English
        Locale('es', 'ES'), // Spanish
      ],
      localizationsDelegates: const [
        AppLocalizations.delegate,
        GlobalMaterialLocalizations.delegate,
        GlobalWidgetsLocalizations.delegate,
        GlobalCupertinoLocalizations.delegate,
      ],
      localeResolutionCallback: (locale, supportedLocales) {
        for (var supportedLocale in supportedLocales) {
          if (supportedLocale.languageCode == locale?.languageCode &&
              supportedLocale.countryCode == locale?.countryCode) {
            return supportedLocale;
          }
        }
        return const Locale('en', 'US');
      },
    );
  }
}

// ✅ Error Widget in case Firebase fails
class ErrorApp extends StatelessWidget {
  final String errorMessage;

  const ErrorApp({super.key, required this.errorMessage});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        backgroundColor: Colors.white,
        body: Center(
          child: Padding(
            padding: const EdgeInsets.all(20.0),
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                const Icon(Icons.error_outline, color: Colors.red, size: 80),
                const SizedBox(height: 20),
                const Text(
                  '⚠️ Error initializing Firebase!',
                  style: TextStyle(fontSize: 22, color: Colors.red),
                  textAlign: TextAlign.center,
                ),
                const SizedBox(height: 10),
                Text(
                  errorMessage,
                  style: const TextStyle(fontSize: 16, color: Colors.black54),
                  textAlign: TextAlign.center,
                ),
                const SizedBox(height: 20),
                ElevatedButton(
                  onPressed: () {
                    // Restart the app if Firebase fails
                    main();
                  },
                  style: ElevatedButton.styleFrom(
                    backgroundColor: Colors.teal,
                    padding: const EdgeInsets.symmetric(
                      horizontal: 30,
                      vertical: 12,
                    ),
                  ),
                  child: const Text(
                    'Retry',
                    style: TextStyle(fontSize: 18, color: Colors.white),
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
