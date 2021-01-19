----------------------------
ZoneId
----------------------------
	# 时区对象

	# 静态属性
		public static final Map<String, String> SHORT_IDS;
		static {
			Map<String, String> map = new HashMap<>(64);
			map.put("ACT", "Australia/Darwin");
			map.put("AET", "Australia/Sydney");
			map.put("AGT", "America/Argentina/Buenos_Aires");
			map.put("ART", "Africa/Cairo");
			map.put("AST", "America/Anchorage");
			map.put("BET", "America/Sao_Paulo");
			map.put("BST", "Asia/Dhaka");
			map.put("CAT", "Africa/Harare");
			map.put("CNT", "America/St_Johns");
			map.put("CST", "America/Chicago");
			map.put("CTT", "Asia/Shanghai");
			map.put("EAT", "Africa/Addis_Ababa");
			map.put("ECT", "Europe/Paris");
			map.put("IET", "America/Indiana/Indianapolis");
			map.put("IST", "Asia/Kolkata");
			map.put("JST", "Asia/Tokyo");
			map.put("MIT", "Pacific/Apia");
			map.put("NET", "Asia/Yerevan");
			map.put("NST", "Pacific/Auckland");
			map.put("PLT", "Asia/Karachi");
			map.put("PNT", "America/Phoenix");
			map.put("PRT", "America/Puerto_Rico");
			map.put("PST", "America/Los_Angeles");
			map.put("SST", "Pacific/Guadalcanal");
			map.put("VST", "Asia/Ho_Chi_Minh");
			map.put("EST", "-05:00");
			map.put("MST", "-07:00");
			map.put("HST", "-10:00");
			SHORT_IDS = Collections.unmodifiableMap(map);
		}
	
	# 静态方法
		ZoneId systemDefault()
		Set<String> getAvailableZoneIds()
		ZoneId of(String zoneId, Map<String, String> aliasMap)
		ZoneId of(String zoneId)
		ZoneId ofOffset(String prefix, ZoneOffset offset)
		ZoneId of(String zoneId, boolean checkAvailable)
		ZoneId ofWithPrefix(String zoneId, int prefixLength, boolean checkAvailable) 
		ZoneId from(TemporalAccessor temporal)
	
	# 实例方法
		String getId()
		String getDisplayName(TextStyle style, Locale locale)
		ZoneRules getRules()
		ZoneId normalized()

