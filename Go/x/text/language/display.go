------------------------
display
------------------------
	# 提供了所请求语言的语言、脚本和区域的显示名称。
		* 这些数据基于 CLDR 的 localeDisplayNames
	

------------------------
var
------------------------
	const CLDRVersion = "32"
	
	const Version = "32"
	
	var (
		// Supported lists the languages for which names are defined.
		Supported language.Coverage

		// The set of all possible values for which names are defined. Note that not
		// all Namer implementations will cover all the values of a given type.
		// A Namer will return the empty string for unsupported values.
		Values language.Coverage
	)

------------------------
type
------------------------
	# type Dictionary struct {
		// contains filtered or unexported fields
		}
		var (
			Afrikaans            *Dictionary = &af        // af
			Amharic              *Dictionary = &am        // am
			Arabic               *Dictionary = &ar        // ar
			ModernStandardArabic *Dictionary = Arabic     // ar-001
			Azerbaijani          *Dictionary = &az        // az
			Bulgarian            *Dictionary = &bg        // bg
			Bengali              *Dictionary = &bn        // bn
			Catalan              *Dictionary = &ca        // ca
			Czech                *Dictionary = &cs        // cs
			Danish               *Dictionary = &da        // da
			German               *Dictionary = &de        // de
			Greek                *Dictionary = &el        // el
			English              *Dictionary = &en        // en
			AmericanEnglish      *Dictionary = English    // en-US
			BritishEnglish       *Dictionary = English    // en-GB
			Spanish              *Dictionary = &es        // es
			EuropeanSpanish      *Dictionary = Spanish    // es-ES
			LatinAmericanSpanish *Dictionary = Spanish    // es-419
			Estonian             *Dictionary = &et        // et
			Persian              *Dictionary = &fa        // fa
			Finnish              *Dictionary = &fi        // fi
			Filipino             *Dictionary = &fil       // fil
			French               *Dictionary = &fr        // fr
			Gujarati             *Dictionary = &gu        // gu
			Hebrew               *Dictionary = &he        // he
			Hindi                *Dictionary = &hi        // hi
			Croatian             *Dictionary = &hr        // hr
			Hungarian            *Dictionary = &hu        // hu
			Armenian             *Dictionary = &hy        // hy
			Indonesian           *Dictionary = &id        // id
			Icelandic            *Dictionary = &is        // is
			Italian              *Dictionary = &it        // it
			Japanese             *Dictionary = &ja        // ja
			Georgian             *Dictionary = &ka        // ka
			Kazakh               *Dictionary = &kk        // kk
			Khmer                *Dictionary = &km        // km
			Kannada              *Dictionary = &kn        // kn
			Korean               *Dictionary = &ko        // ko
			Kirghiz              *Dictionary = &ky        // ky
			Lao                  *Dictionary = &lo        // lo
			Lithuanian           *Dictionary = &lt        // lt
			Latvian              *Dictionary = &lv        // lv
			Macedonian           *Dictionary = &mk        // mk
			Malayalam            *Dictionary = &ml        // ml
			Mongolian            *Dictionary = &mn        // mn
			Marathi              *Dictionary = &mr        // mr
			Malay                *Dictionary = &ms        // ms
			Burmese              *Dictionary = &my        // my
			Nepali               *Dictionary = &ne        // ne
			Dutch                *Dictionary = &nl        // nl
			Norwegian            *Dictionary = &no        // no
			Punjabi              *Dictionary = &pa        // pa
			Polish               *Dictionary = &pl        // pl
			Portuguese           *Dictionary = &pt        // pt
			BrazilianPortuguese  *Dictionary = Portuguese // pt-BR
			EuropeanPortuguese   *Dictionary = &ptPT      // pt-PT
			Romanian             *Dictionary = &ro        // ro
			Russian              *Dictionary = &ru        // ru
			Sinhala              *Dictionary = &si        // si
			Slovak               *Dictionary = &sk        // sk
			Slovenian            *Dictionary = &sl        // sl
			Albanian             *Dictionary = &sq        // sq
			Serbian              *Dictionary = &sr        // sr
			SerbianLatin         *Dictionary = &srLatn    // sr
			Swedish              *Dictionary = &sv        // sv
			Swahili              *Dictionary = &sw        // sw
			Tamil                *Dictionary = &ta        // ta
			Telugu               *Dictionary = &te        // te
			Thai                 *Dictionary = &th        // th
			Turkish              *Dictionary = &tr        // tr
			Ukrainian            *Dictionary = &uk        // uk
			Urdu                 *Dictionary = &ur        // ur
			Uzbek                *Dictionary = &uz        // uz
			Vietnamese           *Dictionary = &vi        // vi
			Chinese              *Dictionary = &zh        // zh
			SimplifiedChinese    *Dictionary = Chinese    // zh-Hans
			TraditionalChinese   *Dictionary = &zhHant    // zh-Hant
			Zulu                 *Dictionary = &zu        // zu
		)

	# type Formatter struct {
			// contains filtered or unexported fields
		}
		func Language(lang interface{}) Formatter
		func Region(region interface{}) Formatter
		func Script(script interface{}) Formatter
		func Tag(tag interface{}) Formatter
		func (f Formatter) Format(state format.State, verb rune)
	
	# type Namer interface {
			// Name returns a display string for the given value. A Namer returns an
			// empty string for values it does not support. A Namer may support naming
			// an unspecified value. For example, when getting the name for a region for
			// a tag that does not have a defined Region, it may return the name for an
			// unknown region. It is up to the user to filter calls to Name for values
			// for which one does not want to have a name string.
			Name(x interface{}) string
		}
		func Languages(t language.Tag) Namer
		func Regions(t language.Tag) Namer
		func Scripts(t language.Tag) Namer
		func Tags(t language.Tag) Namer
	
	# type SelfNamer struct {
			// Supported defines the values supported by this Namer.
			Supported language.Coverage
		}
		func (n SelfNamer) Name(x interface{}) string

------------------------
func
------------------------
