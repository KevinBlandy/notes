---------------------
language
---------------------

---------------------
var
---------------------

	const (
		// Replace deprecated base languages with their preferred replacements.
		DeprecatedBase CanonType = 1 << iota
		// Replace deprecated scripts with their preferred replacements.
		DeprecatedScript
		// Replace deprecated regions with their preferred replacements.
		DeprecatedRegion
		// Remove redundant scripts.
		SuppressScript
		// Normalize legacy encodings. This includes legacy languages defined in
		// CLDR as well as bibliographic codes defined in ISO-639.
		Legacy
		// Map the dominant language of a macro language group to the macro language
		// subtag. For example cmn -> zh.
		Macro
		// The CLDR flag should be used if full compatibility with CLDR is required.
		// There are a few cases where language.Tag may differ from CLDR. To follow all
		// of CLDR's suggestions, use All|CLDR.
		CLDR

		// Raw can be used to Compose or Parse without Canonicalization.
		Raw CanonType = 0

		// Replace all deprecated tags with their preferred replacements.
		Deprecated = DeprecatedBase | DeprecatedScript | DeprecatedRegion

		// All canonicalizations recommended by BCP 47.
		BCP47 = Deprecated | SuppressScript

		// All canonicalizations.
		All = BCP47 | Legacy | Macro

		// Default is the canonicalization used by Parse, Make and Compose. To
		// preserve as much information as possible, canonicalizations that remove
		// potentially valuable information are not included. The Matcher is
		// designed to recognize similar tags that would be the same if
		// they were canonicalized using All.
		Default = Deprecated | Legacy
	)

	const CLDRVersion = "32"

	const NumCompactTags = compact.NumCompactTags

	var ErrMissingLikelyTagsData = errors.New("missing likely tags data")

---------------------
type
---------------------
	# type Base struct {
			// contains filtered or unexported fields
		}
		func MustParseBase(s string) Base
		func ParseBase(s string) (Base, error)
		func (b Base) ISO3() string
		func (b Base) IsPrivateUse() bool
		func (b Base) String() string
	
	# type CanonType int
		func (c CanonType) Canonicalize(t Tag) (Tag, error)
		func (c CanonType) Compose(part ...interface{}) (t Tag, err error)
		func (c CanonType) Make(s string) Tag
		func (c CanonType) MustParse(s string) Tag
		func (c CanonType) Parse(s string) (t Tag, err error)

	# type Confidence int
		func Comprehends(speaker, alternative Tag) Confidence
		func (c Confidence) String() string
	
	# type Coverage interface {
			Tags() []Tag
			BaseLanguages() []Base
			Scripts() []Script
			Regions() []Region
		}
		func NewCoverage(list ...interface{}) Coverage
	
	# type Extension struct {
		}
		func ParseExtension(s string) (e Extension, err error)
		func (e Extension) String() string
		func (e Extension) Tokens() []string
		func (e Extension) Type() byte
	
	# type MatchOption func(*matcher)
		func PreferSameScript(preferSame bool) MatchOption
	
	# type Matcher interface {
			Match(t ...Tag) (tag Tag, index int, c Confidence)
		}
		func NewMatcher(t []Tag, options ...MatchOption) Matcher
	
	# type Region struct {
		// contains filtered or unexported fields
		}
		func EncodeM49(r int) (Region, error)
		func MustParseRegion(s string) Region
		func ParseRegion(s string) (Region, error)
		func (r Region) Canonicalize() Region
		func (r Region) Contains(c Region) bool
		func (r Region) ISO3() string
		func (r Region) IsCountry() bool
		func (r Region) IsGroup() bool
		func (r Region) IsPrivateUse() bool
		func (r Region) M49() int
		func (r Region) String() string
		func (r Region) TLD() (Region, error)
	
	# type Script struct {
			// contains filtered or unexported fields
		}
		func MustParseScript(s string) Script
		func ParseScript(s string) (Script, error)
		func (s Script) IsPrivateUse() bool
		func (s Script) String() string
	
	# type Tag compact.Tag
		var (
			Und Tag = Tag{}

			Afrikaans            Tag = Tag(compact.Afrikaans)
			Amharic              Tag = Tag(compact.Amharic)
			Arabic               Tag = Tag(compact.Arabic)
			ModernStandardArabic Tag = Tag(compact.ModernStandardArabic)
			Azerbaijani          Tag = Tag(compact.Azerbaijani)
			Bulgarian            Tag = Tag(compact.Bulgarian)
			Bengali              Tag = Tag(compact.Bengali)
			Catalan              Tag = Tag(compact.Catalan)
			Czech                Tag = Tag(compact.Czech)
			Danish               Tag = Tag(compact.Danish)
			German               Tag = Tag(compact.German)
			Greek                Tag = Tag(compact.Greek)
			English              Tag = Tag(compact.English)
			AmericanEnglish      Tag = Tag(compact.AmericanEnglish)
			BritishEnglish       Tag = Tag(compact.BritishEnglish)
			Spanish              Tag = Tag(compact.Spanish)
			EuropeanSpanish      Tag = Tag(compact.EuropeanSpanish)
			LatinAmericanSpanish Tag = Tag(compact.LatinAmericanSpanish)
			Estonian             Tag = Tag(compact.Estonian)
			Persian              Tag = Tag(compact.Persian)
			Finnish              Tag = Tag(compact.Finnish)
			Filipino             Tag = Tag(compact.Filipino)
			French               Tag = Tag(compact.French)
			CanadianFrench       Tag = Tag(compact.CanadianFrench)
			Gujarati             Tag = Tag(compact.Gujarati)
			Hebrew               Tag = Tag(compact.Hebrew)
			Hindi                Tag = Tag(compact.Hindi)
			Croatian             Tag = Tag(compact.Croatian)
			Hungarian            Tag = Tag(compact.Hungarian)
			Armenian             Tag = Tag(compact.Armenian)
			Indonesian           Tag = Tag(compact.Indonesian)
			Icelandic            Tag = Tag(compact.Icelandic)
			Italian              Tag = Tag(compact.Italian)
			Japanese             Tag = Tag(compact.Japanese)
			Georgian             Tag = Tag(compact.Georgian)
			Kazakh               Tag = Tag(compact.Kazakh)
			Khmer                Tag = Tag(compact.Khmer)
			Kannada              Tag = Tag(compact.Kannada)
			Korean               Tag = Tag(compact.Korean)
			Kirghiz              Tag = Tag(compact.Kirghiz)
			Lao                  Tag = Tag(compact.Lao)
			Lithuanian           Tag = Tag(compact.Lithuanian)
			Latvian              Tag = Tag(compact.Latvian)
			Macedonian           Tag = Tag(compact.Macedonian)
			Malayalam            Tag = Tag(compact.Malayalam)
			Mongolian            Tag = Tag(compact.Mongolian)
			Marathi              Tag = Tag(compact.Marathi)
			Malay                Tag = Tag(compact.Malay)
			Burmese              Tag = Tag(compact.Burmese)
			Nepali               Tag = Tag(compact.Nepali)
			Dutch                Tag = Tag(compact.Dutch)
			Norwegian            Tag = Tag(compact.Norwegian)
			Punjabi              Tag = Tag(compact.Punjabi)
			Polish               Tag = Tag(compact.Polish)
			Portuguese           Tag = Tag(compact.Portuguese)
			BrazilianPortuguese  Tag = Tag(compact.BrazilianPortuguese)
			EuropeanPortuguese   Tag = Tag(compact.EuropeanPortuguese)
			Romanian             Tag = Tag(compact.Romanian)
			Russian              Tag = Tag(compact.Russian)
			Sinhala              Tag = Tag(compact.Sinhala)
			Slovak               Tag = Tag(compact.Slovak)
			Slovenian            Tag = Tag(compact.Slovenian)
			Albanian             Tag = Tag(compact.Albanian)
			Serbian              Tag = Tag(compact.Serbian)
			SerbianLatin         Tag = Tag(compact.SerbianLatin)
			Swedish              Tag = Tag(compact.Swedish)
			Swahili              Tag = Tag(compact.Swahili)
			Tamil                Tag = Tag(compact.Tamil)
			Telugu               Tag = Tag(compact.Telugu)
			Thai                 Tag = Tag(compact.Thai)
			Turkish              Tag = Tag(compact.Turkish)
			Ukrainian            Tag = Tag(compact.Ukrainian)
			Urdu                 Tag = Tag(compact.Urdu)
			Uzbek                Tag = Tag(compact.Uzbek)
			Vietnamese           Tag = Tag(compact.Vietnamese)
			Chinese              Tag = Tag(compact.Chinese)
			SimplifiedChinese    Tag = Tag(compact.SimplifiedChinese)
			TraditionalChinese   Tag = Tag(compact.TraditionalChinese)
			Zulu                 Tag = Tag(compact.Zulu)
		)

		func Compose(part ...interface{}) (t Tag, err error)
		func Make(s string) Tag
		func MatchStrings(m Matcher, lang ...string) (tag Tag, index int)
		func MustParse(s string) Tag
		func Parse(s string) (t Tag, err error)
		func ParseAcceptLanguage(s string) (tag []Tag, q []float32, err error)

		func (t Tag) Base() (Base, Confidence)
		func (t Tag) Extension(x byte) (ext Extension, ok bool)
		func (t Tag) Extensions() []Extension
		func (t Tag) IsRoot() bool
		func (t Tag) MarshalText() (text []byte, err error)
		func (t Tag) Parent() Tag
		func (t Tag) Raw() (b Base, s Script, r Region)
		func (t Tag) Region() (Region, Confidence)
		func (t Tag) Script() (Script, Confidence)
		func (t Tag) SetTypeForKey(key, value string) (Tag, error)
		func (t Tag) String() string
		func (t Tag) TypeForKey(key string) string
		func (t *Tag) UnmarshalText(text []byte) error
		func (t Tag) Variants() []Variant

	# type ValueError interface {
			error
			// Subtag returns the subtag for which the error occurred.
			Subtag() string
		}

	# type Variant struct {
			// contains filtered or unexported fields
		}
		func ParseVariant(s string) (Variant, error)
		func (v Variant) String() string

---------------------
func
---------------------

	func CompactIndex(t Tag) (index int, exact bool)