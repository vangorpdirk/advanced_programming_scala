package utilities

class LanguageManager
{
  def setLanguage(languageString: String): String =
  {
    val language = languageString

    language match
    {
      case "dutch" => "resources/languagetxtfiles/Alldata Dutch.txt"
      case "danish" => "resources/languagetxtfiles/Alldata Danish.txt"
      case "finnish" => "resources/languagetxtfiles/Alldat Finnish.txt"
      case "english" => "resources/languagetxtfiles/Alldata English.txt"
      case "german" => "resources/languagetxtfiles/All data German.txt"
      case "portuguese" => "resources/languagetxtfiles/All data Portuguese.txt"
      case "italian" => "resources/languagetxtfiles/All data Italian.txt"
      case "spanish" => "resources/languagetxtfiles/All data Spanish.txt"
    }
  }

  def setAlphabet(languageString: String): String =
  {
    val alphabet = "alphabet" + languageString

    alphabet match
    {
      case "alphabetdutch" => "abcdefghijklmnopqrstuvwxyz"
      case "alphabetdanish" => "abcdefghijklmnopqrstuvwxyzæåø"
      case "alphabetfinnish" => "abcdefghijklmnopqrstuvwxyzäö"
      case "alphabetenglish" => "abcdefghijklmnopqrstuvwxyz"
      case "alphabetgerman" => "abcdefghijklmnopqrstuvwxyzäöüß"
      case "alphabetportugese" => "abcdefghijklmnopqrstuvwxyz"
      case "alphabetitalian" => "abcdefghilmnopqrstuvz"
      case "alphabetspanish" => "abcdefghijklmnñopqrstuvwxyz"
    }
  }

  //vowels
  def setVowels(languageString: String): String =
  {
    val alphabet = "alphabet" + languageString

    alphabet match
    {
      case "alphabetdutch" => "aeuio"
      case "alphabetdanish" => "aeiouæåø"
      case "alphabetfinnish" => "aeiouäö"
      case "alphabetenglish" => "aeiou"
      case "alphabetgerman" => "aeiouäöü"
      case "alphabetportugese" => "aeiou"
      case "alphabetitalian" => "aeiou"
      case "alphabetspanish" => "aeiou"
    }
  }
}
