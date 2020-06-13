package utilities

class LanguageManager
{
  def setLanguage(languageString: String): String =
  {
    languageString match
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

  //all letters of alphabet
  def setAlphabet(languageString: String): String =
  {
    languageString match
    {
      case "dutch" => "abcdefghijklmnopqrstuvwxyz"
      case "danish" => "abcdefghijklmnopqrstuvwxyzæåø"
      case "finnish" => "abcdefghijklmnopqrstuvwxyzäö"
      case "english" => "abcdefghijklmnopqrstuvwxyz"
      case "german" => "abcdefghijklmnopqrstuvwxyzäöüß"
      case "portugese" => "abcdefghijklmnopqrstuvwxyz"
      case "italian" => "abcdefghilmnopqrstuvz"
      case "spanish" => "abcdefghijklmnñopqrstuvwxyz"
    }
  }

  //vowels
  def setVowels(languageString: String): String =
  {
    languageString match
    {
      case "dutch" => "aeuio"
      case "danish" => "aeiouæåø"
      case "finnish" => "aeiouäö"
      case "english" => "aeiou"
      case "german" => "aeiouäöü"
      case "portugese" => "aeiou"
      case "italian" => "aeiou"
      case "spanish" => "aeiou"
    }
  }
}
