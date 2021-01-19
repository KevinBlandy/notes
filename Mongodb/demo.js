print(' -------------- Mongodb -------------- ')


const list = [
{
	"account_number":1,
	"balance":39225,
	"firstname":"Amber",
	"lastname":"Duke",
	"age":32,
	"gender":"M",
	"address":"880 Holmes Lane",
	"employer":"Pyrami",
	"email":"amberduke@pyrami.com",
	"city":"Brogan",
	"state":"IL"
}
,{"account_number":6,"balance":5686,"firstname":"Hattie","lastname":"Bond","age":36,"gender":"M","address":"671 Bristol Street","employer":"Netagy","email":"hattiebond@netagy.com","city":"Dante","state":"TN"}
,{"account_number":13,"balance":32838,"firstname":"Nanette","lastname":"Bates","age":28,"gender":"F","address":"789 Madison Street","employer":"Quility","email":"nanettebates@quility.com","city":"Nogal","state":"VA"}
,{"account_number":18,"balance":4180,"firstname":"Dale","lastname":"Adams","age":33,"gender":"M","address":"467 Hutchinson Court","employer":"Boink","email":"daleadams@boink.com","city":"Orick","state":"MD"}
,{"account_number":20,"balance":16418,"firstname":"Elinor","lastname":"Ratliff","age":36,"gender":"M","address":"282 Kings Place","employer":"Scentric","email":"elinorratliff@scentric.com","city":"Ribera","state":"WA"}
,{"account_number":25,"balance":40540,"firstname":"Virginia","lastname":"Ayala","age":39,"gender":"F","address":"171 Putnam Avenue","employer":"Filodyne","email":"virginiaayala@filodyne.com","city":"Nicholson","state":"PA"}
,{"account_number":32,"balance":48086,"firstname":"Dillard","lastname":"Mcpherson","age":34,"gender":"F","address":"702 Quentin Street","employer":"Quailcom","email":"dillardmcpherson@quailcom.com","city":"Veguita","state":"IN"}
,{"account_number":37,"balance":18612,"firstname":"Mcgee","lastname":"Mooney","age":39,"gender":"M","address":"826 Fillmore Place","employer":"Reversus","email":"mcgeemooney@reversus.com","city":"Tooleville","state":"OK"}
,{"account_number":44,"balance":34487,"firstname":"Aurelia","lastname":"Harding","age":37,"gender":"M","address":"502 Baycliff Terrace","employer":"Orbalix","email":"aureliaharding@orbalix.com","city":"Yardville","state":"DE"}
,{"account_number":70,"balance":38172,"firstname":"Deidre","lastname":"Thompson","age":33,"gender":"F","address":"685 School Lane","employer":"Netplode","email":"deidrethompson@netplode.com","city":"Chestnut","state":"GA"}
,{"account_number":75,"balance":40500,"firstname":"Sandoval","lastname":"Kramer","age":22,"gender":"F","address":"166 Irvington Place","employer":"Overfork","email":"sandovalkramer@overfork.com","city":"Limestone","state":"NH"}
,{"account_number":82,"balance":41412,"firstname":"Concetta","lastname":"Barnes","age":39,"gender":"F","address":"195 Bayview Place","employer":"Fitcore","email":"concettabarnes@fitcore.com","city":"Summerfield","state":"NC"}
,{"account_number":87,"balance":1133,"firstname":"Hewitt","lastname":"Kidd","age":22,"gender":"M","address":"446 Halleck Street","employer":"Isologics","email":"hewittkidd@isologics.com","city":"Coalmont","state":"ME"}
,{"account_number":94,"balance":41060,"firstname":"Brittany","lastname":"Cabrera","age":30,"gender":"F","address":"183 Kathleen Court","employer":"Mixers","email":"brittanycabrera@mixers.com","city":"Cornucopia","state":"AZ"}
,{"account_number":99,"balance":47159,"firstname":"Ratliff","lastname":"Heath","age":39,"gender":"F","address":"806 Rockwell Place","employer":"Zappix","email":"ratliffheath@zappix.com","city":"Shaft","state":"ND"}
,{"account_number":114,"balance":43045,"firstname":"Josephine","lastname":"Joseph","age":31,"gender":"F","address":"451 Oriental Court","employer":"Turnabout","email":"josephinejoseph@turnabout.com","city":"Sedley","state":"AL"}
,{"account_number":119,"balance":49222,"firstname":"Laverne","lastname":"Johnson","age":28,"gender":"F","address":"302 Howard Place","employer":"Senmei","email":"lavernejohnson@senmei.com","city":"Herlong","state":"DC"}
,{"account_number":121,"balance":19594,"firstname":"Acevedo","lastname":"Dorsey","age":32,"gender":"M","address":"479 Nova Court","employer":"Netropic","email":"acevedodorsey@netropic.com","city":"Islandia","state":"CT"}
,{"account_number":126,"balance":3607,"firstname":"Effie","lastname":"Gates","age":39,"gender":"F","address":"620 National Drive","employer":"Digitalus","email":"effiegates@digitalus.com","city":"Blodgett","state":"MD"}
,{"account_number":133,"balance":26135,"firstname":"Deena","lastname":"Richmond","age":36,"gender":"F","address":"646 Underhill Avenue","employer":"Sunclipse","email":"deenarichmond@sunclipse.com","city":"Austinburg","state":"SC"}
,{"account_number":152,"balance":8088,"firstname":"Wolfe","lastname":"Rocha","age":21,"gender":"M","address":"457 Guernsey Street","employer":"Hivedom","email":"wolferocha@hivedom.com","city":"Adelino","state":"MS"}
,{"account_number":157,"balance":39868,"firstname":"Claudia","lastname":"Terry","age":20,"gender":"F","address":"132 Gunnison Court","employer":"Lumbrex","email":"claudiaterry@lumbrex.com","city":"Castleton","state":"MD"}
,{"account_number":164,"balance":9101,"firstname":"Cummings","lastname":"Little","age":26,"gender":"F","address":"308 Schaefer Street","employer":"Comtrak","email":"cummingslittle@comtrak.com","city":"Chaparrito","state":"WI"}
,{"account_number":169,"balance":45953,"firstname":"Hollie","lastname":"Osborn","age":34,"gender":"M","address":"671 Seaview Court","employer":"Musaphics","email":"hollieosborn@musaphics.com","city":"Hanover","state":"GA"}
,{"account_number":171,"balance":7091,"firstname":"Nelda","lastname":"Hopper","age":39,"gender":"M","address":"742 Prospect Place","employer":"Equicom","email":"neldahopper@equicom.com","city":"Finderne","state":"SC"}
,{"account_number":176,"balance":18607,"firstname":"Kemp","lastname":"Walters","age":28,"gender":"F","address":"906 Howard Avenue","employer":"Eyewax","email":"kempwalters@eyewax.com","city":"Why","state":"KY"}
,{"account_number":190,"balance":3150,"firstname":"Blake","lastname":"Davidson","age":30,"gender":"F","address":"636 Diamond Street","employer":"Quantasis","email":"blakedavidson@quantasis.com","city":"Crumpler","state":"KY"}
,{"account_number":195,"balance":5025,"firstname":"Kaye","lastname":"Gibson","age":31,"gender":"M","address":"955 Hopkins Street","employer":"Zork","email":"kayegibson@zork.com","city":"Ola","state":"WY"}
,{"account_number":203,"balance":21890,"firstname":"Eve","lastname":"Wyatt","age":33,"gender":"M","address":"435 Furman Street","employer":"Assitia","email":"evewyatt@assitia.com","city":"Jamestown","state":"MN"}
,{"account_number":208,"balance":40760,"firstname":"Garcia","lastname":"Hess","age":26,"gender":"F","address":"810 Nostrand Avenue","employer":"Quiltigen","email":"garciahess@quiltigen.com","city":"Brooktrails","state":"GA"}
,{"account_number":210,"balance":33946,"firstname":"Cherry","lastname":"Carey","age":24,"gender":"M","address":"539 Tiffany Place","employer":"Martgo","email":"cherrycarey@martgo.com","city":"Fairacres","state":"AK"}
,{"account_number":215,"balance":37427,"firstname":"Copeland","lastname":"Solomon","age":20,"gender":"M","address":"741 McDonald Avenue","employer":"Recognia","email":"copelandsolomon@recognia.com","city":"Edmund","state":"ME"}
,{"account_number":222,"balance":14764,"firstname":"Rachelle","lastname":"Rice","age":36,"gender":"M","address":"333 Narrows Avenue","employer":"Enaut","email":"rachellerice@enaut.com","city":"Wright","state":"AZ"}
,{"account_number":227,"balance":19780,"firstname":"Coleman","lastname":"Berg","age":22,"gender":"M","address":"776 Little Street","employer":"Exoteric","email":"colemanberg@exoteric.com","city":"Eagleville","state":"WV"}
,{"account_number":234,"balance":44207,"firstname":"Betty","lastname":"Hall","age":37,"gender":"F","address":"709 Garfield Place","employer":"Miraclis","email":"bettyhall@miraclis.com","city":"Bendon","state":"NY"}
,{"account_number":239,"balance":25719,"firstname":"Chang","lastname":"Boyer","age":36,"gender":"M","address":"895 Brigham Street","employer":"Qaboos","email":"changboyer@qaboos.com","city":"Belgreen","state":"NH"}
,{"account_number":241,"balance":25379,"firstname":"Schroeder","lastname":"Harrington","age":26,"gender":"M","address":"610 Tapscott Avenue","employer":"Otherway","email":"schroederharrington@otherway.com","city":"Ebro","state":"TX"}
,{"account_number":246,"balance":28405,"firstname":"Katheryn","lastname":"Foster","age":21,"gender":"F","address":"259 Kane Street","employer":"Quantalia","email":"katherynfoster@quantalia.com","city":"Bath","state":"TX"}
,{"account_number":253,"balance":20240,"firstname":"Melissa","lastname":"Gould","age":31,"gender":"M","address":"440 Fuller Place","employer":"Buzzopia","email":"melissagould@buzzopia.com","city":"Lumberton","state":"MD"}
,{"account_number":258,"balance":5712,"firstname":"Lindsey","lastname":"Hawkins","age":37,"gender":"M","address":"706 Frost Street","employer":"Enormo","email":"lindseyhawkins@enormo.com","city":"Gardners","state":"AK"}
,{"account_number":260,"balance":2726,"firstname":"Kari","lastname":"Skinner","age":30,"gender":"F","address":"735 Losee Terrace","employer":"Singavera","email":"kariskinner@singavera.com","city":"Rushford","state":"WV"}
,{"account_number":265,"balance":46910,"firstname":"Marion","lastname":"Schneider","age":26,"gender":"F","address":"574 Everett Avenue","employer":"Evidends","email":"marionschneider@evidends.com","city":"Maplewood","state":"WY"}
,{"account_number":272,"balance":19253,"firstname":"Lilly","lastname":"Morgan","age":25,"gender":"F","address":"689 Fleet Street","employer":"Biolive","email":"lillymorgan@biolive.com","city":"Sunbury","state":"OH"}
,{"account_number":277,"balance":29564,"firstname":"Romero","lastname":"Lott","age":31,"gender":"M","address":"456 Danforth Street","employer":"Plasto","email":"romerolott@plasto.com","city":"Vincent","state":"VT"}
,{"account_number":284,"balance":22806,"firstname":"Randolph","lastname":"Banks","age":29,"gender":"M","address":"875 Hamilton Avenue","employer":"Caxt","email":"randolphbanks@caxt.com","city":"Crawfordsville","state":"WA"}
,{"account_number":296,"balance":24606,"firstname":"Rosa","lastname":"Oliver","age":34,"gender":"M","address":"168 Woodbine Street","employer":"Idetica","email":"rosaoliver@idetica.com","city":"Robinson","state":"WY"}
,{"account_number":304,"balance":28647,"firstname":"Palmer","lastname":"Clark","age":35,"gender":"M","address":"866 Boulevard Court","employer":"Maximind","email":"palmerclark@maximind.com","city":"Avalon","state":"NH"}
,{"account_number":309,"balance":3830,"firstname":"Rosemarie","lastname":"Nieves","age":30,"gender":"M","address":"206 Alice Court","employer":"Zounds","email":"rosemarienieves@zounds.com","city":"Ferney","state":"AR"}
,{"account_number":311,"balance":13388,"firstname":"Vinson","lastname":"Ballard","age":23,"gender":"F","address":"960 Glendale Court","employer":"Gynk","email":"vinsonballard@gynk.com","city":"Fairforest","state":"WY"}
,{"account_number":316,"balance":8214,"firstname":"Anita","lastname":"Ewing","age":32,"gender":"M","address":"396 Lombardy Street","employer":"Panzent","email":"anitaewing@panzent.com","city":"Neahkahnie","state":"WY"}
,{"account_number":323,"balance":42230,"firstname":"Chelsea","lastname":"Gamble","age":34,"gender":"F","address":"356 Dare Court","employer":"Isosphere","email":"chelseagamble@isosphere.com","city":"Dundee","state":"MD"}
,{"account_number":328,"balance":12523,"firstname":"Good","lastname":"Campbell","age":27,"gender":"F","address":"438 Hicks Street","employer":"Gracker","email":"goodcampbell@gracker.com","city":"Marion","state":"CA"}
,{"account_number":330,"balance":41620,"firstname":"Yvette","lastname":"Browning","age":34,"gender":"F","address":"431 Beekman Place","employer":"Marketoid","email":"yvettebrowning@marketoid.com","city":"Talpa","state":"CO"}
,{"account_number":335,"balance":35433,"firstname":"Vera","lastname":"Hansen","age":24,"gender":"M","address":"252 Bushwick Avenue","employer":"Zanilla","email":"verahansen@zanilla.com","city":"Manila","state":"TN"}
,{"account_number":342,"balance":33670,"firstname":"Vivian","lastname":"Wells","age":36,"gender":"M","address":"570 Cobek Court","employer":"Nutralab","email":"vivianwells@nutralab.com","city":"Fontanelle","state":"OK"}
,{"account_number":347,"balance":36038,"firstname":"Gould","lastname":"Carson","age":24,"gender":"F","address":"784 Pulaski Street","employer":"Mobildata","email":"gouldcarson@mobildata.com","city":"Goochland","state":"MI"}
,{"account_number":354,"balance":21294,"firstname":"Kidd","lastname":"Mclean","age":22,"gender":"M","address":"691 Saratoga Avenue","employer":"Ronbert","email":"kiddmclean@ronbert.com","city":"Tioga","state":"ME"}
,{"account_number":359,"balance":29927,"firstname":"Vanessa","lastname":"Harvey","age":28,"gender":"F","address":"679 Rutledge Street","employer":"Zentime","email":"vanessaharvey@zentime.com","city":"Williston","state":"IL"}
,{"account_number":361,"balance":23659,"firstname":"Noreen","lastname":"Shelton","age":36,"gender":"M","address":"702 Tillary Street","employer":"Medmex","email":"noreenshelton@medmex.com","city":"Derwood","state":"NH"}
]

db.user.insert(list);