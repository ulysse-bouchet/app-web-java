$(document).ready(function(){
    const login = $("#login");
    const lblLogin = $("#lbl-login");
    const pwd = $("#pwd");
    const lblPwd = $("#lbl-pwd");

    //When the user focuses on a field, its color changes
    login.focusin(() => {
        lblLogin.css({"color" : "var(--light-blue)", "font-size" : "3vh"});
    });
    login.focusout(() => {
        lblLogin.css({"color" : "black", "font-size" : "2vh"});
    });
    pwd.focusin(() => {
        lblPwd.css({"color" : "var(--light-blue)", "font-size" : "3vh"});
    });
    pwd.focusout(() => {
        lblPwd.css({"color" : "black", "font-size" : "2vh"});
    });
});