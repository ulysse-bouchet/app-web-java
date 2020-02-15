$(document).ready(function(){
    const login = $("#login");
    const lblLogin = $("#lbl-login");
    const pwd = $("#pwd");
    const lblPwd = $("#lbl-pwd");

    login.focusin(() => {
        lblLogin.css({"color" : "#8a2be2", "font-size" : "3vh"});
    });
    login.focusout(() => {
        let id = $(this).id;
        lblLogin.css({"color" : "black", "font-size" : "2vh"});
    });
    pwd.focusin(() => {
        lblPwd.css({"color" : "#8a2be2", "font-size" : "3vh"});
    });
    pwd.focusout(() => {
        let id = $(this).id;
        lblPwd.css({"color" : "black", "font-size" : "2vh"});
    });
});