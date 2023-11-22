import { createRouter, createWebHistory } from 'vue-router';
import TheMainView from '../views/TheMainView.vue';
// import TheMemberView from '@/views/TheMemberView.vue';
import TheMapView from '@/views/TheMapView.vue';
import TheMyPageView from '@/views/TheMyPageView.vue';
import ThePlanViewVue from '@/views/ThePlanView.vue';
// import MemberModify from '@/components/member/MemberModify.vue';

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      component: TheMainView,
    },
    {
      path: '/map',
      name: 'map',
      component: TheMapView,
      children: [
        {
          path: ':days',
          name: 'plan-detail',
          component: () => import('@/components/map/MapSelect.vue'),
        },
        {
          path: 'setup',
          name: 'plan-setup',
          component: () => import('@/components/map/MapDefault.vue'),
        },
        {
          path: 'result',
          name: 'plan-result',
          component: () => import('@/components/map/MapResult.vue'),
        },
      ],
    },
    {
      path: '/board',
      name: 'board',
      // component: TheBoardView,
      // route level code-splitting
      // this generates a separate chunk (About.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import('../views/TheBoardView.vue'),
      redirect: { name: 'article-list' },
      children: [
        {
          path: 'list',
          name: 'article-list',
          component: () => import('@/components/board/BoardList.vue'),
        },
        {
          path: 'view/:articleno',
          name: 'article-view',
          component: () => import('@/components/board/BoardDetail.vue'),
        },
        {
          path: 'write',
          name: 'article-write',
          component: () => import('@/components/board/BoardWrite.vue'),
        },
        {
          path: 'modify/:articleno',
          name: 'article-modify',
          component: () => import('@/components/board/BoardModify.vue'),
        },
      ],
    },
    {
      path: '/member',
      name: 'member',
      component: () => import('../views/TheMemberView.vue'),
      redirect: { name: 'member-modify' },
      children: [
        {
          path: 'signup',
          name: 'member-signup',
          component: () => import('@/components/member/MemberSignUp.vue'),
        },
        {
          path: 'member-modify/:userId',
          name: 'member-modify',
          component: () => import('@/components/member/MemberModify.vue'),
        },
      ],
    },
    {
      path: '/mypage',
      name: 'mypage',
      component: TheMyPageView,
    },
    {
      path: '/plan',
      name: 'plan',
      component: ThePlanViewVue,
      children:[
        {
          path:'list',
          name:'plan-list',
          component: () =>import('@/components/plan/PlanList.vue')
          
        }
      ]
    },
  ],
});

export default router;
