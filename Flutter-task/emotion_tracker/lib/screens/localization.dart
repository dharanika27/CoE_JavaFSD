import 'package:flutter/widgets.dart';
import 'package:flutter/foundation.dart';

class AppLocalizations {
  final Locale locale;

  AppLocalizations(this.locale);

  static AppLocalizations? of(BuildContext context) {
    return Localizations.of<AppLocalizations>(context, AppLocalizations);
  }

  static const LocalizationsDelegate<AppLocalizations> delegate =
      _AppLocalizationsDelegate();

  // ✅ Add translations with language support
  static final Map<String, Map<String, String>> _localizedValues = {
    'en': {
      'emotion_tracker': 'Emotion Tracker',
      'track_emotions': 'Track Emotions',
      'your_emotion_is': 'Your emotion is',
      'cycle_emotion': 'Cycle Emotion',
      'reset_emotion': 'Reset Emotion',
      'add_custom_emotion': 'Add Custom Emotion',
      'switch_to_spanish': '🇪🇸 Switch to Spanish',
      'switch_to_english': '🇺🇸 Switch to English',
    },
    'es': {
      'emotion_tracker': 'Rastreador de emociones',
      'track_emotions': 'Rastrear emociones',
      'your_emotion_is': 'Tu emoción es',
      'cycle_emotion': 'Ciclo de emociones',
      'reset_emotion': 'Restablecer emoción',
      'add_custom_emotion': 'Agregar emoción personalizada',
      'switch_to_spanish': '🇪🇸 Cambiar a español',
      'switch_to_english': '🇺🇸 Cambiar a inglés',
    },
  };

  // ✅ Translate based on key
  String? translate(String key) {
    return _localizedValues[locale.languageCode]?[key] ?? key;
  }
}

// ✅ Localization Delegate Class
class _AppLocalizationsDelegate
    extends LocalizationsDelegate<AppLocalizations> {
  const _AppLocalizationsDelegate();

  @override
  bool isSupported(Locale locale) => ['en', 'es'].contains(locale.languageCode);

  @override
  Future<AppLocalizations> load(Locale locale) async {
    return SynchronousFuture<AppLocalizations>(AppLocalizations(locale));
  }

  @override
  bool shouldReload(_AppLocalizationsDelegate old) => false;
}
