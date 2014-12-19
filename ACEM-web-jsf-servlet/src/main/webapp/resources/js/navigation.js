function selectMenuitemLink(allLinksBlock,link) {
	$(allLinksBlock).find(".ui-state-active").removeClass("ui-state-active");
	$(link).addClass("ui-state-active");
}
