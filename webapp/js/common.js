$(document).ready(function(){
    // nav login 분기 처리
    $("#link-login").hide();
    $("#link-logout").hide();
    $("#link-recipe").hide();
    if( getCookie("login") === "true" ){
        $("#link-logout").show();
        $("#link-recipe").show();
    } else {
        $("#link-login").show();
    }
});

document.addEventListener("DOMContentLoaded", function (event) {
    navbarToggleSidebar();
    // closeMenuBeforeGoingToPage();
    navActivePage();
});

function getParams(){
    return Object.fromEntries(new URLSearchParams(window.location.search).entries());
}

function getParam(key){
    return Object.fromEntries(new URLSearchParams(window.location.search).entries())[key];
}

function goHome(){
    location.href = "/";
}

function getCookie(name) {
    var value = document.cookie.match('(^|;) ?' + name + '=([^;]*)(;|$)');
    return value ? value[2] : null;
}