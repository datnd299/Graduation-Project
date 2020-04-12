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
          component: () => import("@/views/pages/Dashboard2.vue")
        },
        {
          path: "/other",
          name: "other",
          component: ()=> import("@/views/theme/BlankWrapper.vue"),
          children:[
            {
              path: "/my-info",
              name: "my-info",
              component: () => import("@/views/pages/other/UserInfo.vue")
            }
          ]
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
              path: "party-b",
              name: "admin-party-a-list",
              component: () => import("@/views/pages/admin/party-b/index.vue")
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
          
        },
        {
          path: "/party-a",
          name: "party-a",
          component: ()=> import("@/views/theme/BlankWrapper.vue"),
          children: [
            {
              path: "accounts",
              name: "party-a-accounts",
              component: () => import("@/views/pages/party-a/accounts/index.vue")
            },
            {
              path: "places",
              name: "party-a-places",
              component: () => import("@/views/pages/party-a/places/index.vue")
            },
            {
              path: "partner",
              name: "party-a-partner",
              component: () => import("@/views/pages/party-a/partners/index.vue")
            },
            {
              path: "/party-a/places/map",
              name: "party-a-places-map",
              component: () => import("@/views/pages/party-a/places/MapView.vue")
            },
            {
              path: "/party-a/tasks",
              name: "party-a-tasks",
              component: () => import("@/views/pages/tasks/PTATasks.vue")
            },
            {
              path: "/party-a/signboards",
              name: "party-a-signboards",
              component: () => import("@/views/pages/party-a/signboards/index.vue")
            },
            {
              path: "/party-a/signboards/new-signboard",
              name: "party-a-signboards-new",
              component: () => import("@/views/pages/party-a/signboards/components/SignboardDetail.vue")
            },
            {
              path: "/party-a/tasks/new-task",
              name: "party-a-task-new-task",
              component: () => import("@/views/pages/tasks/task-detail/index.vue")
            }
            
          ]
          
        },

        {
          path: "/party-b",
          name: "party-b",
          component: ()=> import("@/views/theme/BlankWrapper.vue"),
          children: [
            {
              path: "places-for-rent",
              name: "places-for-rent",
              component: () => import("@/views/pages/party-b/place-for-rent/index.vue")
            },
            {
              path: "places-rental",
              name: "places-rental",
              component: () => import("@/views/pages/party-b/place-rental/index.vue")
            },
            {
              path: "places-rental/:id/details",
              name: "places-rental-details",
              component: () => import("@/views/pages/party-b/place-rental/PlaceRentalDetail.vue")
            },
            
            {
              path: ":id/places",
              name: "places-for-rent-of-party",
              component: () => import("@/views/pages/party-b/place-for-rent/PartyPlaces.vue")
            },
            {
              path: "new-place-for-rent",
              name: "new-place-for-rent",
              component: () => import("@/views/pages/party-b/place-for-rent/components/PlaceInfoEditable.vue")
            },
            {
              path: "/place/:id/details",
              name: "place-details",
              component: () => import("@/views/pages/party-b/place-for-rent/components/PlaceDetails.vue")
            },
            
          ]
        },
        
      ]
    },
    {
      path:'/login',
      component: () => import("@/views/pages/auth/Login2.vue"),
      name:'lg',
      children: [
        
        {
          path: "/login",
          name: "login",
          component: () => import("@/views/pages/auth/components/Login.vue")
        },
        {
          path:'/signup',
          component: () => import("@/views/pages/auth/components/Signup.vue"),
          name:'signup'
        },
      ]
    },
    {
      path:'/qr-scanner',
      component: () => import("@/views/theme/Base.vue"),
      children: [
        
        {
          path: "/qr-scanner",
          name: "qr-scanner",
          component: () => import("@/views/pages/qr/index.vue")
        }
      ]
    },
    {
      path:'/x',
      component: () => import("@/views/theme/BlankWrapper.vue"),
      children: [
        
        {
          path: "/x/t",
          name: "dashboard",
          component: () => import("@/views/pages/admin/party-a/index.vue")
        },
        
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
