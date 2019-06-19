$(function(){

    $("#nav-placeholder").load("modules/nav.html")

    
    var $loggedUser = JSON.parse(localStorage.getItem("user"));
    if($loggedUser==null){
        alert("Morate biti ulogovani da bi ste pristupili ovoj stranici.")
        location.href = "/webp/";
    }
    console.log($loggedUser.admin);
    if(!$loggedUser.admin){
        console.log($loggedUser.admin);

        alert("Nemate dozvolu da pristupite ovoj stranici");
        location.href = "/webp/";
    }

    getAll();

    $("#searchByUsername").click(function(){
        $("#search-placeholder").load("modules/searchByUsername.html");
    });

    $(".getAll").click(function(){
        getAll();
        $("#search-placeholder").empty();
        $("#userRoleFilter button").removeClass("active");
        $("#getAllBtn").addClass("active");
        
    });

    $("#getAllAdminsBtn").click(function(){
        $("#userRoleFilter button").removeClass("active");
        $(this).addClass("active");
        $.get("http://localhost:8080/webp/KorisnikServlet?role=admin",function(data,status,xhr){
            if(status=="success"){
                renderTable(data);
            }
        });
    });

    $("#getAllUsersBtn").click(function(){
        $("#userRoleFilter button").removeClass("active");
        $(this).addClass("active");
        $.get("http://localhost:8080/webp/KorisnikServlet?role=user",function(data,status,xhr){
            if(status=="success"){
                renderTable(data);
            }
        });
    });

    function getAll(){
        $.get("http://localhost:8080/webp/KorisnikServlet",function(data,status,xhr){
            if(status=="success"){
                renderTable(data);
            }
        });
    }

});

$(document).on("click","a.delete",function(event){
    $username = $(document.activeElement).val();
    if($loggedUser.korisnickoIme==$username){
        alert("Ne mozete sami sebe izbrisati.")
        return;
    }
    if(confirm("Da li ste sigurni?")){
        $.ajax({
            type:'DELETE',
            url:'http://localhost:8080/webp/KorisnikSer11vlet?username='+$username,
            success:function(){
                location.reload();
            }
        })
    }
});

function renderTable(data){
    $("#user-table tbody tr").remove();
    $.each(JSON.parse(data), function (i, user) {
        var $blokiranSpan;
        var $blokiran;
        if(user.blokiran){
            $blokiranSpan = $("<span>&bull;</span>");
            $blokiranSpan.addClass("status text-danger")
            $blokiran="Blokiran";
        } else {
            $blokiranSpan = $("<span>&bull;</span>");
            $blokiranSpan.addClass("status text-success")
            $blokiran="Nije blokiran";
        }

        $iconSpan2 = $("<i>&#xE5C9;</i>").addClass("material-icons");
        $a2 = $('<a>').attr("href","#").attr("title","Block").val(user.korisnickoIme).addClass('delete').append($iconSpan2);

         $tr = $('<tr>').append(
            $('<td>').append($('<a>').attr("href","/webp/my-profile.html?username="+user.korisnickoIme).text(user.korisnickoIme)),
            $('<td>').text(user.datumRegistracije.replace("T"," ")),
            $('<td>').text((user.admin) ? "Administrator" : "Korisnik"),
            $('<td>').text($blokiran).prepend($blokiranSpan),
            $('<td>').append($a2)

        ).appendTo($("#user-table tbody"))

        
    });
    $("#user-table").DataTable();
}