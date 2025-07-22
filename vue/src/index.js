Page({
    data: {
        code: "",      // 存储登录返回的 code
        openid: ""     // 存储服务端返回的 openid
    },

    // 点击按钮触发登录
    handleLogin() {
        wx.login({
            success: (res) => {
                if (res.code) {
                    console.log("临时 code:", res.code);
                } else {
                    console.error("获取 code 失败:", res.errMsg);
                }
            },
            fail: (err) => {
                console.error("登录接口调用失败:", err);
            }
        });
    },

    // 发送 code 到服务端（示例）
    sendCodeToServer(code) {
        wx.request({
            url: "https://你的服务端域名/login", // 替换为你的接口地址
            method: "POST",
            data: { code: code },
            success: (res) => {
                if (res.data.openid) {
                    this.setData({ openid: res.data.openid });
                }
            },
            fail: (err) => {
                console.error("请求服务端失败:", err);
            }
        });
    }
});