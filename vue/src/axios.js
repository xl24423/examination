import axios from "axios";
import router from "./router";
import Element from "element-ui"

axios.defaults.baseURL = "http://localhost:9090"
axios.defaults.headers.common['Authorization'] = localStorage.getItem('token')
const request = axios.create({
	timeout: 5000,
	headers: {
		'Content-Type': "application/json; charset=utf-8"
	}
})
request.interceptors.response.use(response => {

		let res = response.data
		console.log(res)
		if (res.code === 200) {
			return response
		} else {
			Element.Message.error(res.msg,{duration : 3*1000})

			return Promise.reject(response.data.msg)
		}
	},
	error => {

		console.log(error)

		if (error.response.data != null) {
			error.massage = error.response.data.msg
		}

		if (error.response.status === 401) {
			router.push("/login")
		}

		Element.Message.error(error.massage, {duration: 3000})
		return Promise.reject(error)
	}
)

export default request