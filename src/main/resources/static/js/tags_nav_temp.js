Vue.component("tags-app",{
    props:["data"],
    template:`
     <div class="nav font-weight-light">
    <a href="tag_question.html?id=0" class="nav-item nav-link text-info"><small>全部</small></a>
    <a :href="'tag_question.html?id='+tag.id" class="nav-item nav-link text-info"
       v-for = "tag in data"
    ><small v-text="tag.name"></small></a>
  </div>
    `

})