import json
data = [
    {"Id":1,"ParentId":None,"Sort":0,"Name":"菜单1"},
    {"Id":2,"ParentId":1,"Sort":0,"Name":"菜单1-1"},
    {"Id":3,"ParentId":1,"Sort":0,"Name":"菜单1-2"},
    {"Id":4,"ParentId":2,"Sort":2,"Name":"菜单1-1-2"},
    {"Id":5,"ParentId":2,"Sort":1,"Name":"菜单1-1-1"},
    {"Id":6,"ParentId":None,"Sort":0,"Name":"菜单2"},
    {"Id":7,"ParentId":6,"Sort":0,"Name":"菜单2-1"},
    {"Id":8,"ParentId":6,"Sort":0,"Name":"菜单2-2"},
    {"Id":9,"ParentId":8,"Sort":2,"Name":"菜单2-2-2"},
    {"Id":10,"ParentId":8,"Sort":1,"Name":"菜单2-2-1"},
    {"Id":11,"ParentId":10,"Sort":0,"Name":"菜单2-2-1-1"},
    {"Id":12,"ParentId":10,"Sort":0,"Name":"菜单2-2-1-2"},
]

class Tree(object):
    def __init__(self,node):
        self.id = node["Id"]
        self.parentId = node["ParentId"]
        self.sort = node["Sort"]
        self.name = node["Name"]
        self.children = []
    
    def sub_test(self,node):
        if node["ParentId"] == self.id:
            self.children.append(Tree(node))
            data.remove(node)
        else:
            if len(self.children) > 0:
                for item in self.children:
                    item.sub_test(node)
    
    def format(self):
        result = {"Id":self.id,"ParentId":self.parentId,"Sort":self.sort,"Name":self.name}
        if len(self.children) > 0:
            result["children"] = []
            for item in self.children:
                result["children"].append(item.format())
        return result
    
    def __str__(self):
        return json.dumps(self.format(),ensure_ascii = False)
    
trees = []

for item in data:
    if item["ParentId"] is None:
        trees.append(Tree(item))
        data.remove(item)
        
while len(data) > 0:
    for item in data:
        for tree in trees:
            tree.sub_test(item)
            
for tree in trees:
    print(tree)

'''JavaScript
    const data = new Set([
        {"Id":1,"ParentId":null,"Sort":0,"Name":"菜单1"},
        {"Id":2,"ParentId":1,"Sort":0,"Name":"菜单1-1"},
        {"Id":3,"ParentId":1,"Sort":0,"Name":"菜单1-2"},
        {"Id":4,"ParentId":2,"Sort":2,"Name":"菜单1-1-2"},
        {"Id":5,"ParentId":2,"Sort":1,"Name":"菜单1-1-1"},
        {"Id":6,"ParentId":null,"Sort":0,"Name":"菜单2"},
        {"Id":7,"ParentId":6,"Sort":0,"Name":"菜单2-1"},
        {"Id":8,"ParentId":6,"Sort":0,"Name":"菜单2-2"},
        {"Id":9,"ParentId":8,"Sort":2,"Name":"菜单2-2-2"},
        {"Id":10,"ParentId":8,"Sort":1,"Name":"菜单2-2-1"},
        {"Id":11,"ParentId":10,"Sort":0,"Name":"菜单2-2-1-1"},
        {"Id":12,"ParentId":10,"Sort":0,"Name":"菜单2-2-1-2"},
    ])
    
    class Tree{
        constructor(node){
            this.id = node.Id;
            this.parentId = node.ParentId;
            this.sort = node.Sort;
            this.name = node.Name;
            this.children = [];
        }
        subTest(node){
            if (node.ParentId == this.id){
                this.children.push(new Tree(node));
                data.delete(node);
            }else{
                if (this.children.length > 0){
                    for(let item of this.children){
                        item.subTest(node)
                    }
                }
            }
        }
    }
    
    let trees = [];
    
    for (let item of data){
        if (item.ParentId == null){
            trees.push(new Tree(item))
            data.delete(item)
        }
    }
            
    while (data.size > 0){
        for(let item of data){
            for(tree of trees){
                tree.subTest(item)
            }
        }
    }
                
    console.log(JSON.stringify(trees));
'''
        
