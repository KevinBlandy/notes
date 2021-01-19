
import dataclasses

@dataclasses.dataclass
class User():
    # 声明属性名称以及属性的类型
    id: int
    name: str
    # 默认值
    join: bool = True
    
    def __str__(self):
        return 'id=%d,name=%s,join=%s' % (self.id, self.name, self.join)

print(User(1, 'KevinBlandy'))  # id=1,name=KevinBlandy,join=True
print(User(1, 'KevinBlandy', False))  # id=1,name=KevinBlandy,join=False
