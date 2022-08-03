import { createStore } from 'vuex'
import { user } from "./modules/user"

import home from './modules/home'
import jobopening from './modules/jobopening'
import board from './modules/board'
import category from './modules/category'

export default createStore({
  modules: { home, user, board, jobopening, category },
})
