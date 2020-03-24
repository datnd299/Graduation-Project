import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
  routes: [
    {
      path: "/",
      redirect: "/dashboard",
      component: () => import("@/views/theme/Base"),
      children: [
        
        {
          path: "/dashboard",
          name: "dashboard",
          component: () => import("@/views/pages/Dashboard.vue")
        },
        {
          path: "/admin",
          name: "admin",
          component: ()=> import("@/views/theme/BlankWrapper.vue"),
          children: [
            {
              path: "party-a",
              name: "admin-party-a-list",
              component: () => import("@/views/pages/admin/party-a/index.vue")
            },
            {
              path: "party-a/:id",
              redirect:'party-a/:id/users',
              component: ()=> import("@/views/theme/BlankWrapper.vue"),
              children: [
                {
                  path: "users",
                  name: "admin-party-a-list",
              component: () => import("@/views/pages/admin/party-a/party-a-acc.vue"),
                }]
            }
          ]
          
        }
      ]
    },
    {
      path:'/login',
      component: () => import("@/views/theme/BlankWrapper.vue"),
      name:'login'
      
    },
    {
      path:'/x',
      component: () => import("@/views/theme/BlankWrapper.vue"),
      children: [
        
        {
          path: "/x/t",
          name: "dashboard",
          component: () => import("@/views/pages/admin/party-a/index.vue")
        }
      ]
    },
       
    {
      // the 404 route, when none of the above matches
      path: "/404",
      name: "404",
      component: () => import("@/views/pages/error/Error-1.vue")
    },
    {
      path:'/*',
      redirect:'/404'
    }
    
  ]
});
