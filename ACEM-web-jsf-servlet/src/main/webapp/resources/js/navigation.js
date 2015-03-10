function selectMenuitemLink(allLinksBlock,link) {
	$(allLinksBlock).find(".ui-state-active").removeClass("ui-state-active");
	if ($(link)!==null) {
	    $(link).addClass("ui-state-active");
	}
}
