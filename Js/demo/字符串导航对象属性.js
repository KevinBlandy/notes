
/*
 * 通过字符串导航读取对象的属性，非常适合用于读取配置/国际化
 * 参考自 Vue 祖师爷 https://cn.vuejs.org/guide/reusability/plugins.html#writing-a-plugin
 */

function config (obj: any, key: string) :any  {
    return key.split('.').reduce((pre: any, attr: string) :any  => {
        if (pre){
            return pre[attr]
        }
    }, obj) || null;
}

const settings = {
    app: {
        jwt: {
            key: '123456',
            duration: 1231,
        }
    },
    user: {
        session: {
            timeout: '30h'
        }
    }
}

console.log(config(settings, 'app.jwt.duration'));          // 1231
console.log(config(settings, 'user.session.timeout'));      // 30h
console.log(config(settings, 'app.jwt'));                   // {key: '123456', duration: 1231}
console.log(config(settings, 'app.jwt.undefined'));         // null
