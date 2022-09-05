import http from "@/utils/request";

 
export function paperResult(query) {
    return new Promise((resolve, reject) => {
        http.get("/paperResult", {
            params: query
        }).then(res => {
            resolve(res.data);
        }).catch(err => {
            reject(err.data)
        })
    });
}

