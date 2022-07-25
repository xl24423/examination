Vue.component("view-app",{
    props:["data"],
    template:`<div class="container-fluid bg-light mt-5">
        <h4 class="m-2 p-2 font-weight-light"><i class="fa fa-list" aria-hidden="true"></i> 热点问题</h4>
        <div class="list-group list-group-flush" v-for="viewQuestion in data">
          <a href="question/detail.html" class="list-group-item list-group-item-action">
            <div class="d-flex w-100 justify-content-between">
              <h6 class="mb-1 text-dark" v-text="viewQuestion.title">测试测试</h6>
            </div>
            <div class="row">
              <div class="col-6">
                <small class="mr-2" v-text="viewQuestion.replyCount>0?viewQuestion.replyCount+'条回答':''"></small>
                <small class="text-warning" v-show="viewQuestion.status==0">未回复</small>
                <small class="text-info" v-show="viewQuestion.status==1">未解决</small>
                <small class="text-success" v-show="viewQuestion.status==2">已解决</small>
              </div>
              <div class="col-6 text-right">
                <small v-text="viewQuestion.pageViews+'浏览'">10浏览</small>
              </div>
            </div>
          </a>
        </div>
      </div>
    `


})