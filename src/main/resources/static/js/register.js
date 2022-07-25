let app = new Vue({
    el:'#app',
    data:{
        inviteCode:'',
        phone:'',
        nickname:'',
        password:'',
        confirm:'',
        message: '',
        hasError: false
    },
    methods:{
        register:function () {
            console.log('Submit');
            let form=new FormData();
            form.append("inviteCode",this.inviteCode);
            form.append("phone",this.phone);
            form.append("nickname",this.nickname);
            form.append("password",this.password);
            form.append("confirm",this.confirm);
            console.log(form);
            if(this.password != this.confirm){
                this.message = "确认密码不一致！";
                this.hasError = true;
                return;
            }
            let data = new FormData(document.querySelector("form"));
            axios.post("/register",data)
                .then(function(res) {
                    if(res.data=="ok"){
                        console.log("注册成功");
                        console.log(res.data);
                        app.hasError = false;
                        location.href = '/login.html?register';
                    }else{
                        app.hasError = true;
                        app.message = res.data;
                    }
                });
        }
    }
});