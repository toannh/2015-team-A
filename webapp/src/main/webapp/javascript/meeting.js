(function($) {
    $(document).ready(function ($) {
        console.log('meeting scheduler');

        $('#UITabPane a').click(function (e) {
            e.preventDefault();
            $(this).tab('show');
        })

        $('input.yesno').each(function(){
            $(this).iphoneStyle({
                disabledClass: 'switchBtnDisabled',
                containerClass: 'uiSwitchBtn',
                labelOnClass: 'switchBtnLabelOn',
                labelOffClass: 'switchBtnLabelOff',
                handleClass: 'switchBtnHandle',
                checkedLabel:'YES',
                uncheckedLabel:'NO',
                onChange: function(el, val) {
                    $(el).trigger('click');
                }
            });
        });

        $("#pending-grid-view").hide();
        window.showList = function() {
            $("#pending-list-view").show();
            $("#pending-grid-view").hide();
            console.log($(this));
        };
        
        window.showGrid = function() {
            $("#pending-grid-view").show();
            $("#pending-list-view").hide();
            console.log($(this));
        };


    });
})($);