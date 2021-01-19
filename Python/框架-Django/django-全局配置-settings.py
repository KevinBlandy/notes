
import os

BASE_DIR = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))


SECRET_KEY = 'uy+yr992j=52fe*6ne%a*(qy8^^w=zubkd2!d)tcdfjc+0yyuv'

# 日志级别DBUG
DEBUG = True

# 日志配置
LOGGING = {
	'version':1,
	'disable_existing_loggers':False,
	'handlers':{
		'console':{
			'level':'DEBUG',
			'class':'logging.StreamHandler',
		}
	},
	'loggers':{
		'django.db.backends':{
			'handlers':['console'],
			'propagate':True,
			'level':'DEBUG'
		},
	}
}

# IP白名单
ALLOWED_HOSTS = []


INSTALLED_APPS = [
    'django.contrib.admin',
    'django.contrib.auth',
    'django.contrib.contenttypes',
    'django.contrib.sessions',
    'django.contrib.messages',
    'django.contrib.staticfiles',
    'blog.apps.BlogConfig',
]

# 中间件
MIDDLEWARE = [
    'django.middleware.security.SecurityMiddleware',
    'django.contrib.sessions.middleware.SessionMiddleware',
    'django.middleware.common.CommonMiddleware',
    'django.middleware.csrf.CsrfViewMiddleware',					# 防止Csrf攻击
    'django.contrib.auth.middleware.AuthenticationMiddleware',
    'django.contrib.messages.middleware.MessageMiddleware',
    'django.middleware.clickjacking.XFrameOptionsMiddleware',
]

# 根ULR映射配置
ROOT_URLCONF = 'website.urls'

# 模版引擎相关的配置
TEMPLATES = [
    {
        'BACKEND': 'django.template.backends.django.DjangoTemplates',
		# 模版所在的目录
        'DIRS': [os.path.join(BASE_DIR, 'templates')] ,
        'APP_DIRS': True,
        'OPTIONS': {
            'context_processors': [
                'django.template.context_processors.debug',
                'django.template.context_processors.request',
                'django.contrib.auth.context_processors.auth',
                'django.contrib.messages.context_processors.messages',
            ],
        },
    },
]


# WEB服务器
WSGI_APPLICATION = 'website.wsgi.application'


# 数据库相关的配置
DATABASES = {
    'default': {
        'ENGINE': 'django.db.backends.sqlite3',			# 数据库引擎
        'NAME': os.path.join(BASE_DIR, 'db.sqlite3'),	
    }
}



AUTH_PASSWORD_VALIDATORS = [
    {
        'NAME': 'django.contrib.auth.password_validation.UserAttributeSimilarityValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.MinimumLengthValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.CommonPasswordValidator',
    },
    {
        'NAME': 'django.contrib.auth.password_validation.NumericPasswordValidator',
    },
]


# 国际化设置
LANGUAGE_CODE = 'en-us'

TIME_ZONE = 'UTC'

USE_I18N = True

USE_L10N = True

USE_TZ = True


# 静态资源访问前缀
STATIC_URL = '/static/'

# 静态资源的目录
STATICFILES_DIRS = (
    [os.path.join(BASE_DIR,'static')]
)
