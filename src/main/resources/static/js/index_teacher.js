
/*
显示当前用户的问题
 */
let questionsApp = new Vue({
    el:'#teacher',
    data: {
        questions:[],
        pageinfo:{},
    },
    methods: {
        loadQuestions:function (pageNum) {
            axios({
                url: '/v1/questions/teacher',
                method: "GET",
                params:{
                    pageNum:pageNum,
                    pageSize:8
                }
            }).then(function(r){
                console.log("成功加载数据");

                if(r.status === 200){
                    questionsApp.questions = r.data.list;
                    questionsApp.pageinfo = r.data;
                    //为question对象添加持续时间属性
                    questionsApp.updateDuration();
                    addTagImage(r.data.list);
                }
            })
        },
        updateDuration: function () {
            let questions = this.questions;
            for (let i = 0; i < questions.length; i++) {
                addDuration(questions[i]);
            }
        }
    },
    created:function () {
        console.log("执行了方法");
        this.loadQuestions(1);
    },
});
let viewQuestions = new Vue({
    el:"#viewQuestions",
    data:{
        views:[],
    },
    created:function () {
        axios.get("/v1/questions/viewsQuestions").then(function (r) {
            viewQuestions.views = r.data;
        })
    }
})













