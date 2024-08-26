---------------------------------
常用的原生 Handler
---------------------------------


---------------------------------
CORS 跨域
---------------------------------

	func cors(handler http.Handler) http.Handler {
		return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			origin := r.Header.Get("Origin")
			if origin != "" {
				w.Header().Set("Access-Control-Allow-Origin", origin)
				requestHeaders := r.Header.Get("Access-Control-Request-Headers")
				if requestHeaders != "" {
					w.Header().Set("Access-Control-Allow-Headers", requestHeaders)
				}
				w.Header().Set("Access-Control-Allow-Credentials", "true")
				w.Header().Set("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS, TRACE")
				w.Header().Set("Access-Control-Expose-Headers", "*")
				w.Header().Set("Access-Control-Max-Age", "3000")

				if r.Method == http.MethodOptions {
					w.WriteHeader(http.StatusNoContent)
					return
				}
			}
			handler.ServeHTTP(w, r)
		})
	}


---------------------------------
Basic 认证
---------------------------------

	func basicAuth(username, password string, handler http.Handler) http.Handler {
		return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			u, p, ok := r.BasicAuth()
			if !ok {
				// 无 Basic 认证信息
				w.Header().Set("WWW-Authenticate", `Basic realm="restricted"`)
				w.WriteHeader(http.StatusUnauthorized)
				return
			}

			if u != username || p != password {
				// 用户名密码错误
				w.WriteHeader(http.StatusUnauthorized)
				return
			}

			handler.ServeHTTP(w, r)
		})
	}

---------------------------------
请求体响应体缓存
---------------------------------
	type cachedResponseWriterWriter struct {
		http.ResponseWriter
		buf *bytes.Buffer
	}

	func (c *cachedResponseWriterWriter) Write(content []byte) (int, error) {
		_, _ = c.buf.Write(content)
		return c.ResponseWriter.Write(content)
	}
	func (c *cachedResponseWriterWriter) Content() []byte {
		return c.buf.Bytes()
	}

	func payload(handler http.Handler) http.Handler {

		return http.HandlerFunc(func(w http.ResponseWriter, r *http.Request) {
			payload, err := io.ReadAll(r.Body)
			if err != nil {
				// TODO 异常处理
				slog.Error("Request Body Read Error", slog.String("err", err.Error()))
				return
			}

			r.Body = io.NopCloser(bytes.NewBuffer(payload))
			w = &cachedResponseWriterWriter{ResponseWriter: w, buf: bytes.NewBuffer(nil)}

			handler.ServeHTTP(w, r)

			slog.Info("Request Body", slog.String("content", string(payload)))
			slog.Info("Response Body", slog.String("content", string(w.(*cachedResponseWriterWriter).Content())))
		})
	}



