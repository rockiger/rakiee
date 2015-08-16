// Compiled by ClojureScript 0.0-2725 {}
goog.provide('akiee.core');
goog.require('cljs.core');
goog.require('akiee.app_db');
goog.require('akiee.sidebar');
goog.require('akiee.node');
goog.require('reagent.core');
goog.require('clojure.browser.repl');
goog.require('akiee.constants');
goog.require('akiee.handlers');
goog.require('clojure.string');
cljs.core.enable_console_print_BANG_.call(null);
/**
* String String String -> Component
* Consumes the text tx, the id and the title t, the state,
* the test function tfn, the on-click function onfn of the button;
* produces the component for the button.
*/
akiee.core.list_state_button = (function list_state_button(tx,id,t,state,tfn,onfn){
var active_QMARK_ = (((cljs.core._EQ_.call(null,tfn.call(null),state)) && (cljs.core.not.call(null,akiee.app_db.editor_QMARK_.call(null))))?"active":"");
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"button.btn.btn-default.navbar-btn.btn-state.toolbar-button","button.btn.btn-default.navbar-btn.btn-state.toolbar-button",-799489622),new cljs.core.PersistentArrayMap(null, 5, [new cljs.core.Keyword(null,"type","type",1174270348),"button",new cljs.core.Keyword(null,"id","id",-1388402092),id,new cljs.core.Keyword(null,"title","title",636505583),t,new cljs.core.Keyword(null,"class","class",-2030961996),active_QMARK_,new cljs.core.Keyword(null,"on-click","on-click",1632826543),onfn], null),tx], null);
});
akiee.core.todo_button = new cljs.core.PersistentVector(null, 7, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.list_state_button,"Todo","show-todo","Ctrl+1",akiee.constants.TODO,akiee.app_db.list_state,akiee.app_db.switch_todo_BANG_], null);
akiee.core.doing_button = new cljs.core.PersistentVector(null, 7, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.list_state_button,"Doing","show-doing","Ctrl+2 / Ctrl+Space",akiee.constants.DOING,akiee.app_db.list_state,akiee.app_db.switch_doing_BANG_], null);
akiee.core.done_button = new cljs.core.PersistentVector(null, 7, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.list_state_button,"Done","show-done","Ctrl+3",akiee.constants.DONE,akiee.app_db.list_state,akiee.app_db.switch_done_BANG_], null);
akiee.core.board_button = new cljs.core.PersistentVector(null, 7, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.list_state_button,"Board","show-all","Ctrl+4",akiee.constants.ALL,akiee.app_db.list_state,akiee.app_db.switch_all_BANG_], null);
/**
* String String String -> Component
* Consumes the icon name in, the id and title t of the button, the test function tfn?,
* the on-click function onfn of the button.
* produces the component for the button.
*/
akiee.core.switch_button = (function switch_button(in$,id,t,tfn_QMARK_,onfn){
var icon_name = [cljs.core.str("fa-"),cljs.core.str(in$)].join('');
var active_QMARK_ = (cljs.core.truth_(tfn_QMARK_.call(null))?"active":"");
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"button.btn.btn-default.navbar-btn.btn-square.toolbar-button","button.btn.btn-default.navbar-btn.btn-square.toolbar-button",-1577311189),new cljs.core.PersistentArrayMap(null, 4, [new cljs.core.Keyword(null,"id","id",-1388402092),id,new cljs.core.Keyword(null,"title","title",636505583),t,new cljs.core.Keyword(null,"class","class",-2030961996),active_QMARK_,new cljs.core.Keyword(null,"on-click","on-click",1632826543),onfn], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"span.fa.fa-fw","span.fa.fa-fw",150090794),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),icon_name], null)], null)], null);
});
akiee.core.editor_switch = new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.switch_button,"code","show-editor","Ctrl+E / Ctrl+Space",akiee.app_db.editor_QMARK_,akiee.app_db.switch_editor_BANG_], null);
akiee.core.search_switch = new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.switch_button,"search","show-searchbox","Ctrl+F",akiee.app_db.search_QMARK_,akiee.app_db.switch_search_BANG_], null);
akiee.core.entry_switch = new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.switch_button,"plus","show-enter-task","Ctrl+Enter",akiee.app_db.entry_QMARK_,akiee.app_db.switch_entry_BANG_], null);
/**
* -> Component
* The toolbar for changing the state of the Akiee
*/
akiee.core.toolbar = (function toolbar(){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"nav#toolbar.navbar.navbar-default","nav#toolbar.navbar.navbar-default",-1269321238),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"role","role",-736691072),"navigation"], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div.container-fluid","div.container-fluid",3929737),new cljs.core.PersistentVector(null, 7, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div.navbar-flex","div.navbar-flex",-790032784),new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div#taskbuttons.btn-group","div#taskbuttons.btn-group",510226813),akiee.core.todo_button,akiee.core.doing_button,akiee.core.done_button], null),akiee.core.board_button,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div.spacer","div.spacer",2037275558)], null),akiee.core.editor_switch,akiee.core.search_switch,akiee.core.entry_switch], null)], null)], null);
});
/**
* ListOf* String -> Component
* Consumes a list of anything loa and a name; produces the component of a select field.
*/
akiee.core.select = (function select(loa,n){
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"select#enter-task-status.form-control","select#enter-task-status.form-control",-1849790969),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"name","name",1843675177),n,new cljs.core.Keyword(null,"defaultValue","defaultValue",-586131910),"Inbox"], null),(function (){var iter__4513__auto__ = (function iter__5164(s__5165){
return (new cljs.core.LazySeq(null,(function (){
var s__5165__$1 = s__5165;
while(true){
var temp__4126__auto__ = cljs.core.seq.call(null,s__5165__$1);
if(temp__4126__auto__){
var s__5165__$2 = temp__4126__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,s__5165__$2)){
var c__4511__auto__ = cljs.core.chunk_first.call(null,s__5165__$2);
var size__4512__auto__ = cljs.core.count.call(null,c__4511__auto__);
var b__5167 = cljs.core.chunk_buffer.call(null,size__4512__auto__);
if((function (){var i__5166 = (0);
while(true){
if((i__5166 < size__4512__auto__)){
var a = cljs.core._nth.call(null,c__4511__auto__,i__5166);
cljs.core.chunk_append.call(null,b__5167,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"option","option",65132272),a], null));

var G__5168 = (i__5166 + (1));
i__5166 = G__5168;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__5167),iter__5164.call(null,cljs.core.chunk_rest.call(null,s__5165__$2)));
} else {
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__5167),null);
}
} else {
var a = cljs.core.first.call(null,s__5165__$2);
return cljs.core.cons.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"option","option",65132272),a], null),iter__5164.call(null,cljs.core.rest.call(null,s__5165__$2)));
}
} else {
return null;
}
break;
}
}),null,null));
});
return iter__4513__auto__.call(null,loa);
})()], null);
});
/**
* ListOfTaskState -> Component
* Consumes the a list of taskstate lot;
* produces the component of the select field for the state of the new task.
*/
akiee.core.enter_task_status = (function enter_task_status(lot){
return akiee.core.select.call(null,lot,"task-status");
});
/**
* ListofString -> Component
* Consumes a list of string los; produces the component for the project select.
*/
akiee.core.enter_task_project = (function enter_task_project(los){
return akiee.core.select.call(null,los,"task-project");
});
/**
* -> Component
* The entry form for entering tasks
*/
akiee.core.enter_task = (function enter_task(){
var show_QMARK_ = (cljs.core.truth_(akiee.app_db.entry_QMARK_.call(null))?new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),""], null):new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),"closed"], null));
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div#enter-task-div.container-fluid.slider","div#enter-task-div.container-fluid.slider",721004260),show_QMARK_,new cljs.core.PersistentVector(null, 7, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"form#enter-task","form#enter-task",-1146655825),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"on-submit","on-submit",1227871159),akiee.handlers.handle_enter_task], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"input#enter-headline.form-control","input#enter-headline.form-control",-66544980),new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"type","type",1174270348),"text",new cljs.core.Keyword(null,"placeholder","placeholder",-104873083),"Enter Headline",new cljs.core.Keyword(null,"name","name",1843675177),"headline"], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.enter_task_status,new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, ["TODO","DOING","DONE"], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.enter_task_project,akiee.app_db.projects.call(null)], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"button.btn.btn-default.btn-on-grey","button.btn.btn-default.btn-on-grey",-1504773694),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"type","type",1174270348),"submit"], null),"Create"], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"button#cancel-enter-task.btn.btn-link","button#cancel-enter-task.btn.btn-link",1812524404),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"type","type",1174270348),"button",new cljs.core.Keyword(null,"on-click","on-click",1632826543),akiee.handlers.cancel_enter_task], null),"Cancel"], null)], null)], null);
});
/**
* -> Component
* The entry form for searching tasks
*/
akiee.core.search = (function search(){
var show_QMARK_ = (cljs.core.truth_(akiee.app_db.search_QMARK_.call(null))?new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),""], null):new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),"closed"], null));
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div#search-form.slider","div#search-form.slider",-201102750),show_QMARK_,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"input#search-input.form-control.mvx-search","input#search-input.form-control.mvx-search",1089613138),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"type","type",1174270348),"text",new cljs.core.Keyword(null,"on-change","on-change",-732046149),akiee.handlers.handle_onchange_search], null)], null)], null);
});
/**
* -> Component
* The textarea to directly edit the task list in markdown
*/
akiee.core.editor = (function editor(){
var show_QMARK_ = (cljs.core.truth_(akiee.app_db.editor_QMARK_.call(null))?new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"style","style",-496642736),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"display","display",242065432),"inline-block"], null)], null):new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"style","style",-496642736),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"display","display",242065432),"none"], null)], null));
return new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div#editor","div#editor",-1877510501),show_QMARK_,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"textarea#editor-area","textarea#editor-area",1920534903),new cljs.core.PersistentArrayMap(null, 2, [new cljs.core.Keyword(null,"rows","rows",850049680),(3),new cljs.core.Keyword(null,"on-blur","on-blur",814300747),akiee.handlers.handle_blur_editor], null)], null)], null);
});
akiee.core.task = (function task(t){
var class$ = ((cljs.core._EQ_.call(null,akiee.app_db.selected.call(null),new cljs.core.Keyword(null,"key","key",-1516042587).cljs$core$IFn$_invoke$arity$1(t)))?"selected":"");
return new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"tr","tr",-1424774646),new cljs.core.PersistentArrayMap(null, 3, [new cljs.core.Keyword(null,"data-key","data-key",1775480631),new cljs.core.Keyword(null,"key","key",-1516042587).cljs$core$IFn$_invoke$arity$1(t),new cljs.core.Keyword(null,"on-click","on-click",1632826543),akiee.handlers.onclick_task,new cljs.core.Keyword(null,"class","class",-2030961996),class$], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"td.taskstate","td.taskstate",-1362714769),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"on-click","on-click",1632826543),akiee.handlers.handle_onclick_taskstate], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"span","span",1394872991),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),"hover-button"], null),new cljs.core.Keyword(null,"todo","todo",-1046442570).cljs$core$IFn$_invoke$arity$1(t)], null)], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"td","td",1479933353),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"span.project-tag.label","span.project-tag.label",-2147232299),new cljs.core.Keyword(null,"project","project",1124394579).cljs$core$IFn$_invoke$arity$1(t)], null),new cljs.core.Keyword(null,"headline","headline",-157157727).cljs$core$IFn$_invoke$arity$1(t)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"td.rank","td.rank",134361831),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"span.fa.fa-chevron-up.hover-button","span.fa.fa-chevron-up.hover-button",-92749066),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"on-click","on-click",1632826543),akiee.handlers.handle_onclick_up], null)], null)], null),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"td.rank","td.rank",134361831),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"span.fa.fa-chevron-down.hover-button","span.fa.fa-chevron-down.hover-button",-1276690443),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"on-click","on-click",1632826543),akiee.handlers.handle_onclick_down], null)], null)], null)], null);
});
akiee.core.task_table = (function task_table(tb){
return new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"table.table","table.table",-538258781),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"tbody","tbody",-80678300),(function (){var iter__4513__auto__ = (function iter__5173(s__5174){
return (new cljs.core.LazySeq(null,(function (){
var s__5174__$1 = s__5174;
while(true){
var temp__4126__auto__ = cljs.core.seq.call(null,s__5174__$1);
if(temp__4126__auto__){
var s__5174__$2 = temp__4126__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,s__5174__$2)){
var c__4511__auto__ = cljs.core.chunk_first.call(null,s__5174__$2);
var size__4512__auto__ = cljs.core.count.call(null,c__4511__auto__);
var b__5176 = cljs.core.chunk_buffer.call(null,size__4512__auto__);
if((function (){var i__5175 = (0);
while(true){
if((i__5175 < size__4512__auto__)){
var t = cljs.core._nth.call(null,c__4511__auto__,i__5175);
cljs.core.chunk_append.call(null,b__5176,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.task,t], null));

var G__5177 = (i__5175 + (1));
i__5175 = G__5177;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__5176),iter__5173.call(null,cljs.core.chunk_rest.call(null,s__5174__$2)));
} else {
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__5176),null);
}
} else {
var t = cljs.core.first.call(null,s__5174__$2);
return cljs.core.cons.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.task,t], null),iter__5173.call(null,cljs.core.rest.call(null,s__5174__$2)));
}
} else {
return null;
}
break;
}
}),null,null));
});
return iter__4513__auto__.call(null,tb);
})()], null)], null);
});
akiee.core.task_list = (function task_list(){
var show_QMARK_ = ((cljs.core.not.call(null,akiee.app_db.editor_QMARK_.call(null)))?new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"style","style",-496642736),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"display","display",242065432),"flex"], null)], null):new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"style","style",-496642736),new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"display","display",242065432),"none"], null)], null));
var sidebar_QMARK_ = (cljs.core.truth_(akiee.app_db.selected.call(null))?new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),""], null):new cljs.core.PersistentArrayMap(null, 1, [new cljs.core.Keyword(null,"class","class",-2030961996),"closed"], null));
return new cljs.core.PersistentVector(null, 4, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div#tasks","div#tasks",-674021829),show_QMARK_,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div#list","div#list",205002526),((cljs.core._EQ_.call(null,akiee.app_db.list_state.call(null),akiee.constants.ALL))?new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"table.table","table.table",-538258781),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"tbody","tbody",-80678300),new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"tr.kanban-row","tr.kanban-row",1298728683),(function (){var iter__4513__auto__ = ((function (show_QMARK_,sidebar_QMARK_){
return (function iter__5182(s__5183){
return (new cljs.core.LazySeq(null,((function (show_QMARK_,sidebar_QMARK_){
return (function (){
var s__5183__$1 = s__5183;
while(true){
var temp__4126__auto__ = cljs.core.seq.call(null,s__5183__$1);
if(temp__4126__auto__){
var s__5183__$2 = temp__4126__auto__;
if(cljs.core.chunked_seq_QMARK_.call(null,s__5183__$2)){
var c__4511__auto__ = cljs.core.chunk_first.call(null,s__5183__$2);
var size__4512__auto__ = cljs.core.count.call(null,c__4511__auto__);
var b__5185 = cljs.core.chunk_buffer.call(null,size__4512__auto__);
if((function (){var i__5184 = (0);
while(true){
if((i__5184 < size__4512__auto__)){
var tb = cljs.core._nth.call(null,c__4511__auto__,i__5184);
cljs.core.chunk_append.call(null,b__5185,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"td.kanban-column","td.kanban-column",-62293854),akiee.core.task_table.call(null,tb)], null));

var G__5186 = (i__5184 + (1));
i__5184 = G__5186;
continue;
} else {
return true;
}
break;
}
})()){
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__5185),iter__5182.call(null,cljs.core.chunk_rest.call(null,s__5183__$2)));
} else {
return cljs.core.chunk_cons.call(null,cljs.core.chunk.call(null,b__5185),null);
}
} else {
var tb = cljs.core.first.call(null,s__5183__$2);
return cljs.core.cons.call(null,new cljs.core.PersistentVector(null, 2, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"td.kanban-column","td.kanban-column",-62293854),akiee.core.task_table.call(null,tb)], null),iter__5182.call(null,cljs.core.rest.call(null,s__5183__$2)));
}
} else {
return null;
}
break;
}
});})(show_QMARK_,sidebar_QMARK_))
,null,null));
});})(show_QMARK_,sidebar_QMARK_))
;
return iter__4513__auto__.call(null,akiee.app_db.tasks.call(null));
})()], null)], null)], null):akiee.core.task_table.call(null,akiee.app_db.tasks.call(null)))], null),new cljs.core.PersistentVector(null, 3, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"aside#task-sidebar","aside#task-sidebar",-1491675059),sidebar_QMARK_,akiee.sidebar.sidebar.call(null)], null)], null);
});
/**
* -> Component
* Produces the base comment
*/
akiee.core.app = (function app(){
return new cljs.core.PersistentVector(null, 6, 5, cljs.core.PersistentVector.EMPTY_NODE, [new cljs.core.Keyword(null,"div#app","div#app",840279329),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.toolbar], null),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.enter_task], null),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.search], null),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.editor], null),new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.task_list], null)], null);
});
akiee.core.big_bang = (function big_bang(){
akiee.handlers.register_keyevents.call(null);

akiee.handlers.register_winevents.call(null);

reagent.core.render_component.call(null,new cljs.core.PersistentVector(null, 1, 5, cljs.core.PersistentVector.EMPTY_NODE, [akiee.core.app], null),document.getElementById("root"));

akiee.sidebar.datepicker_config.call(null);

return akiee.handlers.register_datepicker_events.call(null);
});
akiee.core.big_bang.call(null);

//# sourceMappingURL=core.js.map