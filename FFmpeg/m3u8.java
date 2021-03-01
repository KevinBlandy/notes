------------------------------
m3u8 
------------------------------
	# 参考
		http://www.ffmpeg.org/ffmpeg-formats.html#hls
		http://www.ffmpeg.org/ffmpeg-formats.html#segment
	

	start_number
	hls_time
	hls_list_size
	hls_ts_options
	hls_wrap(废弃)
	hls_allow_cache
	hls_base_url
	hls_segment_filename
	hls_key_info_file
	hls_subtitle_path
	hls_flags
	use_localtime
	use_localtime_mkdir
	hls_playlist_type
	method


------------------------------
hls 参数
------------------------------
	hls_init_time [duration]
	hls_time [duration]
	hls_list_size [size]
	hls_delete_threshold [size]
	hls_ts_options [options_list]
	hls_wrap [wrap]
	hls_start_number_source
		generic 
		epoch
		epoch_us
		datetime
	start_number [number]
	hls_allow_cache [allowcache]
	hls_base_url [baseurl]
	hls_segment_filename [filename]
	use_localtime
	strftimestrftime
	use_localtime_mkdir
	strftime_mkdir
	hls_key_info_file [key_info_file]
	-hls_enc [enc]
	-hls_enc_key [key]
	-hls_enc_key_url [keyurl]
	-hls_enc_iv [iv]
	hls_segment_type [flags]
		mpegts
		fmp4
	hls_fmp4_init_filename [filename]
	hls_fmp4_init_resend
	hls_flags [flags]
		single_file
		delete_segments
		append_list
		round_durations
		discont_start
		omit_endlist
		periodic_rekey
		independent_segments
		iframes_only
		split_by_time
		program_date_time
		second_level_segment_index
		second_level_segment_size
		second_level_segment_duration
		temp_file
	hls_playlist_type [event]
	hls_playlist_type [vod]
	method
	http_user_agent
	var_stream_map
	cc_stream_map
	master_pl_name
	master_pl_publish_rate
	http_persistent
	timeout
	-ignore_io_errors
	headers



------------------------------
segment 参数
------------------------------
	increment_tc 1|0
	reference_stream [specifier]
	segment_format [format]
	segment_format_options [options_list]
	segment_list namesegment_list [name]
	segment_list_flags [flags]
		cache
		live
	segment_list_size [size]
	segment_list_entry_prefix [prefix]
	segment_list_type [type]
		flat
		csv, ext
		ffconcat
		m3u8
	segment_time [time]
	segment_atclocktime 1|0
	segment_clocktime_offset [duration]
	segment_clocktime_wrap_duration [duration]
	segment_time_delta [delta]
	segment_times [times]
	segment_frames [frames]
	segment_wrap [limit]
	segment_start_number [number]
	strftime 1|0
	break_non_keyframes 1|0
	reset_timestamps 1|0
	initial_offset offset
	write_empty_segments 1|0

