(function($) {
    $(document).ready(function ($) {
        console.log('meeting scheduler');

        $('#UITabPane a').click(function (e) {
            e.preventDefault();
            $(this).tab('show');
        })

        $('input.yesno').each(function(){
            console.log(this);
            alert('test');
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
    });
})($);