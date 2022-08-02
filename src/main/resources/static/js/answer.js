let answerApp = new Vue({
        el: '#answerApp',
        data: {
            answers: [],
            answerNum: "",
            editPermission: {},
            commentEditPermission:{},
        },
        methods: {
            loadAllAnswer: function () {
                axios.get("/v1/answer/getAnswersByQuestionId?id=" + id).then(function (r) {
                    if (r.status === 200) {
                        if (r.data === "") {
                            answerApp.answers = null;
                        } else {
                            answerApp.answers = r.data;
                            answerApp.updateDuration();
                            answerApp.updateShow();
                        }
                    } else {
                        alert("服务异常,获取回答失败")
                        location.href = "#";
                    }
                })
            },
            updateDuration: function () {
                let answers = this.answers;
                for (let i = 0; i < answers.length; i++) {
                    addDuration(answers[i]);
                    let comments = answers[i].comments;
                    for (let i=0; i< comments.length; i++){
                        addDuration(comments[i]);
                    }
                }
            },
            updateShow:function (){
                let answers = this.answers;
                let editPermission = this.editPermission;
                for (let i = 0; i < answers.length; i++) {
                    answers[i].show = editPermission[answers[i].id];
                    let comments = answers[i].comments;
                    let commentEditPermission = this.commentEditPermission;
                    for (let i = 0; i < comments.length; i++) {
                        comments[i].show = commentEditPermission[comments[i].id];
                        console.log(comments[i].show)
                    }
                }
            },
            loadAnswerNum: function () {
                axios({
                    url: "/v1/answer/getAnswerNum",
                    method: "GET",
                    params: {
                        id: id,
                    }
                }).then(function (r) {
                    if (isNaN(r.data)) {
                        answerApp.answerNum = r.data;
                    } else {
                        answerApp.answerNum = r.data + "条回答";
                    }
                })
            },
            editPer: function () {
                axios({
                    url: "/v1/answer/editPermissionByNowUser",
                    method: "GET",
                    params: {
                        id: id,
                    },
                }).then(function (r) {
                    answerApp.editPermission = r.data;
                })
            },
            commentEditPer:function () {
                axios.get("/v1/comment/commentEditPermissionByNowUser?id="+id).then(function (r) {
                    answerApp.commentEditPermission = r.data;
                })
            },
            deleteAnswer: function (id) {
                axios.delete("/v1/answer/deleteAnswer?id=" + id).then(function (r){
                    if (r.status ===200){
                        if (r.data===1){
                            alert("删除成功");
                            location.reload();
                        }else {
                            alert(r.data);
                        }
                    }else {
                        alert("请求失败,请稍后再试")
                    }
                })
            },
            postComment:function (answerId) {
                // 新增评论功能,需要 answerId 和 content         ↓↓ textarea前面的空格千万别删
                let textarea = $('#collapseExample'+answerId+" textarea");
                let content = textarea.val();
                    // 创建表单
                    let form = new FormData();
                    form.append("answerId",answerId);
                    form.append("content",content);
                    axios.post("/v1/comment",form).then(function (r) {
                        if (r.status === 200){
                            if (r.data===1){
                                location.reload();
                            }else {
                                alert(r.data+",评论失败")
                            }
                        }else {
                            alert(r.data+",评论失败");
                        }
                    })
            },
            deleteComment:function (id) {

                if (confirm("请确定要删除吗")){
                    axios.delete("/v1/comment/deleteByCommentId?id="+id).then(function (r) {
                        if (r.status === 200){
                            if (r.data===1){
                            location.reload();
                            }else{
                                alert(r.data)
                            }
                        }else {
                            alert("服务器异常,删除失败")
                        }
                    })
                }
            },
            updateComment:function (commentId,index,answer) {
                let textarea = $("#editComment"+commentId+" textarea");
                let content = textarea.val();
                if (!content){
                    return;
                }
                let form = new FormData();
                form.append("answerId",answer.id);
                form.append("content",content);
                axios({
                    url:"/v1/comment/"+commentId+"/update",
                    method:"post",
                    data:form
                }).then(function (r) {

                })
            }
        },
         created: function () {
            this.editPer();
            this.commentEditPer();
            this.loadAllAnswer();
            this.loadAnswerNum();
        }
    }
)

let postAnswerApp = new Vue({
    el:'#postAnswerApp',
    data:{
        permission:false,
    },
    methods:{
        postAnswer:function () {
            let qid = id;
            if (!id || isNaN(id)) {
                alert("地址有误,请重试")
                location.href = "/";
            }else{
                let form = new FormData();
                let content = $('#summernote').val();
                form.append("questionId",qid);
                form.append("content",content);
                axios.post("/v1/answer",form).then(function (r) {

                    let answer = r.data;
                    answer.duration = "刚刚";
                    answer.show = true;
                    answerApp.answers.push(answer);
                    $("#summernote").summernote("reset");

                })
            }
        }
    },
    mounted:function (){
        axios.get("/v1/permission/hasAnswerPermission").then(function (r){
            postAnswerApp.permission = r.data;
        })
    }

})