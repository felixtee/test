$('nav a').parents('li, ul').removeClass('active');
$('a[href="' + this.location.pathname + '"]').parents('li,ul').addClass('active');
if (this.location.pathname == '/' || this.location.pathname == '/signin') {
    $('a[href="/secure/home"]').parents('li,ul').addClass('active');
}
