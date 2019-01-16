function setCookie(key,value,t) {
    var oDate=new Date();
    oDate.setDate(oDate.getDate()+t);
    document.cookie=key+"="+value+"; expires="+oDate.toDateString();
}
function getCookie(key){
    var arr1=document.cookie.split("; ");//由于cookie是通过一个分号+空格的形式串联起来的，所以这里需要先按分号空格截断,变成[name=Jack,pwd=123456,age=22]数组类型；
    for(var i=0;i<arr1.length;i++){
        var arr2=arr1[i].split("=");//通过=截断，把name=Jack截断成[name,Jack]数组；
        if(arr2[0]==key){
            return decodeURI(arr2[1]);
        }
        return ''; //没找到就返回空
    }

}
//封装一个移除cookie的函数
function removeCookie(key){
    setCookie(key,"",-1);//把cookie设置为过期
}

/**
 *
 * 示例二
 * */
function CookieHelper() {

}
//删除cookie
CookieHelper.prototype.deleteCookie = function (name) {
    var date = new Date();
    date.setTime(date.getTime() - 10000);
    document.cookie = name + "=v; expires=" + date.toGMTString() + ";path=/";
}



//设置cookie
CookieHelper.prototype.setCookie = function (name, value) {
    //设置之前先删除
    this.deleteCookie(name);
    //获取当前时间
    var date = new Date();
    var expiresDays = 10;
    //将date设置为10天以后的时间
    date.setTime(date.getTime() + expiresDays * 24 * 3600 * 1000);
    //cookie设置为10天后过期
    document.cookie = name + "=" + value + "; expires=" + date.toGMTString() + ";path=/";
    document.cookie
}

//获取某一cookie key=value
CookieHelper.prototype.getCookie = function (name) {
    var strCookie = document.cookie;
    var arrCookie = strCookie.split("; ");
    for (var i = 0; i < arrCookie.length; i++) {
        var arr = arrCookie[i].split("=");
        if (arr[0] == name) {
            return arr[1];
        }
    }
    return "";
}
//获取cookie 类型 key=id=123&name=qwe&pwd=uio
CookieHelper.prototype.getCookies = function (name) {
    var strCookie = document.cookie;
    var arrCookie = strCookie.split("; ");
    for (var i = 0; i < arrCookie.length; i++) {
        var arr = arrCookie[i].split("=");
        if (arr[0] == name) {
            return arrCookie[i].substring(arrCookie[i].indexOf("=") + 1)
        }
    }
    return "";
}
//获取所有cookie

CookieHelper.prototype.getAllCookie = function (name) {
    var strCookie = document.cookie;
    var arrCookie = strCookie.split("; ");
    if (arrcookie.length > 0) {
        return arrCookie;
    } else {
        return "";
    }
}

/**示例二用法*/
function booking(id, num) {
    if (parseInt(num == 0)) {
        return false;
    } else {
        var startDate = document.getElementById("startDate").value;
        var endDate = document.getElementById("endDate").value;
        //声明封装方法存储Cookie
        var cookiehelp = new CookieHelper();
        var name="Bookingvalue";
        var value = "UnitID=" + id + "&startDate=" + startDate + "&endDate=" + endDate;
        //调用存储Cookie方法
        cookiehelp.setCookie(name, value);

        var startDate = document.getElementById("startDate").value;
        var endDate = document.getElementById("endDate").value;
        window.location.href = "Booking.aspx?UnitID=" + id + "&startDate=" + startDate + "&endDate=" + endDate;
    }
}

/**
 * 示例三
 */
function cookieStorage( maxage, path ){
    var cookie = ( function(){
        var cookie = {};
        var all = document.cookie;
        if( all ==="" ){
            return cookie;
        }
        var list = all.split( "; " );
        for( var i=0; i<list.length; i++ ){
            var singleCookie = list[i];
            var p = singleCookie.indexOf( "=" );
            var name = singleCookie.substring( 0, p );
            var value = singleCookie.substring( p+1 );
            value = decodeURIComponent( value );
            cookie[name] = value;
        }
        return cookie;
    }() );

    var keys = [];
    for( var key in cookie ){
        keys.push( key );
    }

    this.length = keys.length;
    this.key = function( n ){
        if( n < 0 || n > keys.length-1 ){
            return null;
        }
        return keys[n];
    };
    this.getItem = function(){
        return cookie[name] || null;
    };
    this.setItem = function( key, value ){
        if( !( key in cookie ) ){
            keys.push( key );
            this.length ++;
        }
        cookie[key] = value;
        var cookie = key + "=" + encodeURIComponent( value );
        if( maxage ){
            cookie += "; max-age=" + maxage;
        }
        if( path ){
            cookie += "; path=" + path;
        }
        document.cookie = cookie;
    };
    this.removeItem = function( key ){
        if( !( key in cookie ) ){
            return;
        }
        delete cookie[key];
        for( var i=0; i<keys.length; i++ ){
            if( keys[i] === key ){
                keys.splice( i, 1 );
                break;
            }
        }
        this.length--;
        document.cookie = key + "=; max-age=0";
    };
    this.clear = function(){
        for( var i=0; i<keys.length; i++ ){
            document.cookie = keys[i] + "=; max-age=0";
        }
        cookie = {};
        keys = [];
        this.length = 0;
    }
}