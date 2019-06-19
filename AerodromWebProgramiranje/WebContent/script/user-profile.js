$.urlParam = function (name) {
    var results = new RegExp('[\?&]' + name + '=([^&#]*)')
                      .exec(window.location.search);

    return (results !== null) ? results[1] || 0 : false;
}

$(function(){

    var $userName = $.urlParam("username");

    if($userName!=""){
        getUser($userName);
    }

    function getUser($username){
        $.get("http://localhost:8080/webp/user?username="+$username,function(data,status,xhr){
            if(status=="success"){
                console.log(data);
            }
        });
    }

});