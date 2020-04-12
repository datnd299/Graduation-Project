

const state = {
    quickPanelOpen:false,
    numNewNotification:0,
}

const mutations = {
  TOGGLE_QUICKPANEL: state => {
    state.quickPanelOpen = !state.quickPanelOpen
    
  },
  CLOSE_QUICKPANEL: (state) => {
    state.quickPanelOpen = false
  },
  OPEN_QUICKPANEL: (state) => {
    state.quickPanelOpen = true
  },
  SET_NUMNOTIFICATION: (state, num) => {
    state.numNewNotification = num
  },
  UP_NUMNOTIFICATION: (state) => {
    state.numNewNotification = state.numNewNotification+1
  }
}

const actions = {
  toggleQuickPanel({ commit }) {
    commit('TOGGLE_QUICKPANEL')
  },
  closeQuickPanel({ commit }) {
    commit('CLOSE_QUICKPANEL')
  },
  openQuickPanel({ commit }) {
    commit('OPEN_QUICKPANEL')
  },
  setNumNotification({ commit }, num) {
    commit('SET_NUMNOTIFICATION', num)
  },
  upNumNotification({ commit }) {
    commit('UP_NUMNOTIFICATION')
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions,
  getters:{
    quickPanelOpen(state){
        return state.quickPanelOpen
    },
    numNewNotification(state){
        return state.numNewNotification
    }
  }
}

