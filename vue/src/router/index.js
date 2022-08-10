import Vue from 'vue'
import VueRouter from 'vue-router'
import store from "@/store";

Vue.use(VueRouter)

const routes = [
  {
    path: '/',
    component: () => import('../views/Manage.vue'),
    redirect: "/home",
    children: [
      { path: 'home', name: '首页', component: () => import('../views/Home.vue')},
      { path: 'user', name: '用户管理', component: () => import('../views/User.vue')},
      { path: 'person', name: '个人信息',component: () => import('../views/Person')},
      {
        path: '/questionbank',
        name: '题库管理',
        component: () => import('../views/QuestionBank.vue')
      },

      // TestQuestions.vue
      {
        path: '/testquestions',
        name: '试题管理',
        component: () => import('../views/TestQuestions.vue')
      },

      // Examination.vue
      {
        path: '/examination',
        name: '考试管理',
        component: () => import('../views/Examination.vue')
      },
      // onlineexamination
      {
        path: '/onlineexamination',
        name: '考试管理',
        component: () => import('../views/OnlineExamination.vue')
      },
      // prepareexam
      {
        path: '/prepareexam/*',
        name: '考试准备',
        component: () => import('../views/PrepareExam.vue')
      },
      {
        path: '/startexam/*',
        name: '考试准备',
        component: () => import('../views/StartExam.vue')
      }
      ,
      {
        path: '/videoindex',
        name: '考试准备',
        component: () => import('../views/video/videoIndex.vue')
      }
      ,
      {
        path: '/videoinsert',
        name: '考试准备',
        component: () => import('../views/video/videoInsert.vue')
      }
    ]
  },
  {
    path: '/about',
    name: 'About',
    component: () => import('../views/About.vue')
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('../views/Login.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/Register.vue')
  },
  {
    path: '/testquestionsadd',
    name: '添加试题',
    component: () => import('../views/testquestionsAdd.vue')
  },
  {
    path: '/examinationadd',
    name: '添加考试',
    component: () => import('../views/examinationAdd.vue')
  },

]

const router = new VueRouter({
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

// 路由守卫
router.beforeEach((to, from, next) => {
  localStorage.setItem("currentPathName", to.name)  // 设置当前的路由名称，为了在Header组件中去使用
  let token = localStorage.getItem("token")
  if (to.path == "/login" || to.path == '/register'){
    next();
  }else if (!token){
    next({path: "/login"})
  }
  // store.commit("setPath")  // 触发store的数据更新
  next()  // 放行路由
})

export default router

