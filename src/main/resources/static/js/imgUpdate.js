(function (mod) {
    if (typeof exports == "object" && typeof module == "object") // CommonJS
        module.exports = mod();
    else if (typeof define == "function" && define.amd) // AMD
        return define([], mod);
    else // Plain browser env
        window.UploadImage = mod();
})(function () {


    function UploadImage(id, url, key,isbase64) {
        this.element = document.getElementById(id);
        this.url = url; //后端处理图片的路径
        this.imgKey = key || "smfile"; //提到到后端的name
        this.isBase64 = isbase64 || false; // 是否传输base64
    }
    //粘贴图片事件
    UploadImage.prototype.paste = function (callback, formData) {
        var thatthat = this;
        this.element.addEventListener('paste', function (e) {//处理目标容器（id）的paste事件

            if (e.clipboardData && e.clipboardData.items.length > 0 && e.clipboardData.items[0].type.indexOf('image') > -1) {
                var that = this,
                    file = e.clipboardData.items[0].getAsFile();//读取e.clipboardData中的数据

                var fd = formData || (new FormData());
                if(this.isBase64){
                    dataReader(file, function (e) { //reader读取完成后，xhr上传
                        fd.append(thatthat.imgKey, this.result); // this.result得到图片的base64
                    });
                }else {
                    fd.append(thatthat.imgKey,file);
                }
                thatthat.element.innerHTML = "上传中请稍后...";
                xhRequest('POST', thatthat.url, fd, callback, that);
            }
        }, false);

    };

    //拖拽事件
    UploadImage.prototype.drag = function (callback, formData) {
        var that = this;
        this.element.addEventListener('drop', function (e) {//处理目标容器（id）的drop事件
            e.preventDefault(); //取消默认浏览器拖拽效果
            var fileList = e.dataTransfer.files; //获取文件对象
            //检测是否是拖拽文件到页面的操作
            if (fileList.length == 0) {
                return false;
            }
            //检测文件是不是图片
            if (fileList[0].type.indexOf('image') === -1) {
                console.log && console.log("您拖的不是图片！");
                return false;
            }
            var fd = formData || (new FormData());
            if(this.isBase64){
                dataReader(fileList[0], function (e) { //reader读取完成后，xhr上传
                    fd.append(that.imgKey, this.result);
                    // this.result得到图片的base64
                });
            }else {
                fd.append(that.imgKey,fileList[0]);
            }
            that.element.innerHTML = "上传中请稍后...";
            xhRequest('POST', that.url, fd, callback, this);

        }, false);
    };

    //上传
    UploadImage.prototype.upload = function (callback, formData) {
        this.drag(callback, formData);
        this.paste(callback, formData);
    };

    preventDragDefault();
    //private

    function xhRequest(method, url, formData, callback, callbackContext) {
        var xhr = new XMLHttpRequest();
        xhr.open(method, url, true);
        //xhr.setRequestHeader("Authorization","CZQ1ii1mwcOOk2utTZsVI8DBSeVaTtMv");
        //xhr.setRequestHeader("Content-Type","multipart/form-data");
        //xhr.setRequestHeader("Sec-Fetch-Dest","cors");
        //xhr.withCredentials = true;
        xhr.onload = function () {
            callback && callback.call(callbackContext || this, xhr);
        };
        xhr.send(formData || (new FormData()));

    }

    function preventDragDefault()//阻止浏览器默认将图片打开的行为
    {
        document.addEventListener("dragleave", preventDefault);//拖离
        document.addEventListener("drop", preventDefault);//拖后放
        document.addEventListener("dragenter", preventDefault);//拖进
        document.addEventListener("dragover", preventDefault);//拖来拖去
    }

    function preventDefault(e) {
        e.preventDefault();
    }

    function dataReader(file, callback) {
        var reader = new FileReader();
        reader.onload = callback;
        reader.readAsDataURL(file);//获取base64编码
    }
    return UploadImage;
});