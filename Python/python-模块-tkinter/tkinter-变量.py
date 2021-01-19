--------------------------------
tk的变量定义					|
--------------------------------
	* tk定义的一系列变量类(tkvar)
	* 通用的属性,默认值为0
	* 通用的方法
		set()
			* 设置值
		get()
			* 获取值

	
--------------------------------
StringVar						|
--------------------------------
	* 构建一个字符类型的变量
	* 构造函数
		def __init__(self, master=None, value=None, name=None)

--------------------------------
IntVar							|
--------------------------------
	* 构建一个int类型的变量
		def __init__(self, master=None, value=None, name=None):
	* 该变量具备默认值,就是0
	

--------------------------------
constants						|
-------------------------------

NO=FALSE=OFF=0
YES=TRUE=ON=1

# -anchor and -sticky
N='n'
S='s'
W='w'
E='e'
NW='nw'
SW='sw'
NE='ne'
SE='se'
NS='ns'
EW='ew'
NSEW='nsew'
CENTER='center'

# -fill
NONE='none'
X='x'
Y='y'
BOTH='both'

# -side
LEFT='left'
TOP='top'
RIGHT='right'
BOTTOM='bottom'

# -relief
RAISED='raised'
SUNKEN='sunken'
FLAT='flat'
RIDGE='ridge'
GROOVE='groove'
SOLID = 'solid'

# -orient
HORIZONTAL='horizontal'
VERTICAL='vertical'

# -tabs
NUMERIC='numeric'

# -wrap
CHAR='char'
WORD='word'

# -align
BASELINE='baseline'

# -bordermode
INSIDE='inside'
OUTSIDE='outside'

# Special tags, marks and insert positions
SEL='sel'
SEL_FIRST='sel.first'
SEL_LAST='sel.last'
END='end'
INSERT='insert'
CURRENT='current'
ANCHOR='anchor'
ALL='all' # e.g. Canvas.delete(ALL)

# Text widget and button states
NORMAL='normal'
DISABLED='disabled'
ACTIVE='active'
# Canvas state
HIDDEN='hidden'

# Menu item types
CASCADE='cascade'
CHECKBUTTON='checkbutton'
COMMAND='command'
RADIOBUTTON='radiobutton'
SEPARATOR='separator'

# Selection modes for list boxes
SINGLE='single'
BROWSE='browse'
MULTIPLE='multiple'
EXTENDED='extended'

# Activestyle for list boxes
# NONE='none' is also valid
DOTBOX='dotbox'
UNDERLINE='underline'

# Various canvas styles
PIESLICE='pieslice'
CHORD='chord'
ARC='arc'
FIRST='first'
LAST='last'
BUTT='butt'
PROJECTING='projecting'
ROUND='round'
BEVEL='bevel'
MITER='miter'

# Arguments to xview/yview
MOVETO='moveto'
SCROLL='scroll'
UNITS='units'
PAGES='pages'
