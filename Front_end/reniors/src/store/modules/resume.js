
import axios from 'axios'
import drf from '@/api/drf'

export default {
  state: {
    careers : [],
    licenses: [],
  },
  getters: {
    careers : state => state.careers,
    licenses : state => state.licenses,

  },
  mutations: {
    SET_CAREERS : (state, careers) => state.careers = careers,
    SET_LICENSES : (state, licenses) => state.licenses = licenses,
  },

  actions: {
    createCareer({ dispatch, getters }, career){
      axios({
        url: drf.resume.career(),
        method: 'post',
        data: JSON.stringify(career),
        headers: getters.authHeader,
      })
      .then(() => {
        dispatch('fetchCareer')
      })
      .catch((err) => {
        console.log(err)
      })
    },
    createLicense({ dispatch, getters}, license){
      axios({
        url: drf.resume.license(),
        method: 'post',
        data: JSON.stringify(license),
        headers: getters.authHeader,
      })
      .then(() => {
        dispatch('fetchLicense')
      })
      .catch((err) => {
        console.log(err)
      })
    },

    updateCareer({ dispatch, getters }, career ){
      console.log('확인!')
      console.log(career)
      axios({
        url: drf.resume.careerEdit(career.id),
        method: 'put',
        data: JSON.stringify(career),
        headers: getters.authHeader,
      })
      .then(() => {
        dispatch('fetchCareer')
      })
      .catch((err) => {
        console.log(err)
      })
    },
    updateLicense({ dispatch, getters }, license ){

      axios({
        url: drf.resume.licenseEdit(license.id),
        method: 'put',
        data: JSON.stringify(license),
        headers: getters.authHeader,
      })
      .then(() => {
        dispatch('fetchLicense')
      })
      .catch((err) => {
        console.log(err)
      })
    },

    deleteCareer({ dispatch , getters }, careerPk){
      if (confirm('정말 삭제하시겠습니까?')){
        axios({
          url: drf.resume.careerEdit(careerPk),
          method: 'delete',
          headers: getters.authHeader,
        })
        .then(() => {
          dispatch('fetchCareer')
        })
        .catch((err) => {
          console.log(err)
        })
      }
    },

    deleteLicense({ dispatch, getters }, licensePk){
      if (confirm('정말 삭제하시겠습니까?')){
        axios({
          url: drf.resume.licenseEdit(licensePk),
          method: 'delete',
          headers: getters.authHeader,
        })
        .then(() => {
          dispatch('fetchLicense')
        })
        .catch((err) => {
          console.log(err)
        })
      }
    },

    fetchCareer({ commit, getters }){
      axios({
        url : drf.resume.career(),
        method: 'get',
        headers: getters.authHeader
      })
      .then(res => {
        commit('SET_CAREERS', res.data)
      })
    },

    fetchLicense({ commit, getters }){
      axios({
        url : drf.resume.license(),
        method: 'get',
        headers: getters.authHeader
      })
      .then(res => {
        commit('SET_LICENSES', res.data)
      })
      .catch((err) => {
        console.log(err)
      })
    }


  },

  

  modules: {
  }

}