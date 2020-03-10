$(document).ready(function(){
    const title = $("#title");
    const lblTitle = $("#lbl-title");
    const author = $("#author");
    const lblAuthor = $("#lbl-author");

    title.focusin(() => {
        lblTitle.css({"color" : "var(--light-blue)", "font-size" : "3vh"});
    });
    title.focusout(() => {
        lblTitle.css({"color" : "black", "font-size" : "2vh"});
    });
    author.focusin(() => {
        lblAuthor.css({"color" : "var(--light-blue)", "font-size" : "3vh"});
    });
    author.focusout(() => {
        lblAuthor.css({"color" : "black", "font-size" : "2vh"});
    });

    const form = $("#form");

    $("#dvd").on('click', () => {
        form.append("<label for='ageMin' id='lbl-ageMin' class='add'>Âge minimum</label>");
        form.append("<input type='number' id='ageMin' class='add' name='ageMin'>");
    });

    $("#book").on('click', () => {
        $(".add").remove();
    });
});