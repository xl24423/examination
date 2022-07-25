let tagsApp = new Vue({
    el:'#tagsApp',
    data:{
        tags:[]
    },
    methods:{
        loadTags:function () {
            console.log('执行了 loadTags');
            axios({
                url:'/v1/tags',
                method:'GET'
            }).then(function(r){
                if(r.status===200){
                    tagsApp.tags=r.data;
                }
            })
        }
    },
    created:function () {
        this.loadTags();
    }
});