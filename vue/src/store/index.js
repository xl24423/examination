import Vue from 'vue'
import Vuex from 'vuex'
import menus from "./modules/menus";
import { Store } from "storage-timing";
Vue.use(Vuex)

export default new Vuex.Store({
	state: {

	},
	 mutations: {

	},
	actions: {},
	modules: {
		menus
	}
})

