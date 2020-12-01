<template>
  <div id="detail" class="ArticleContentBox">
    <div class="article_header">
      <div class="ArticleTitle">
        <div class="title_area">
          <input
            type="text"
            class="form-control"
            id="subject"
            ref="subject"
            v-if="editable"
            v-model="subject"
          />
          <h3 v-else class="title_text">{{subject}}</h3>
        </div>
      </div>
      <div class="WriterInfo">
        <a href class="thumb">
          <img v-if="imgSrc" :src="imgSrc" alt="프로필 사진" width="47" height="47" />
          <img
            v-else
            src="../../assets/img/BasicProfileIcon.svg"
            alt="프로필 사진"
            width="47"
            height="47"
          />
        </a>
        <div class="profile_area">
          <div class="profile_info">
            <div class="nick_box">
              <a id="writerInfocom2usbase" href="#" role="button" class="nickname">{{nickname}}</a>
              <!---->
            </div>
            <!---->
          </div>
          <div class="article_info">
            <!-- <span class="date">{{createDate}}</span> -->
            <span class="date">{{date}}</span>
          </div>
        </div>
      </div>
      <div class="ArticleTool">
        조회
        <strong class="count">{{views}}</strong>
        <!-- <a href="#" role="button" class="button_comment">
          댓글
          <strong class="num">{{replyCount}}</strong>
        </a>-->
        <img src="../../assets/img/heart2.png" />
        <strong class="num">{{countLike}}</strong>
      </div>
    </div>
    <hr style="border-color: #999999;" />
    <div class="article_container">
      <!---->
      <!---->
      <!---->
      <!---->
      <div class="article_viewer">
        <!---->
        <!---->
        <!---->
        <!---->
        <div class="ContentRenderer">
          <div>
            <div v-show="editable">
              <editor-menu-bar :editor="editor" v-slot="{ commands, isActive }">
                <div class="menubar">
                  <button
                    class="menubar__button"
                    :class="{ 'is-active': isActive.bold() }"
                    @click="commands.bold"
                  >
                    <img class="icon" src="../../assets/img/icons/bold.svg" alt />
                  </button>

                  <button
                    class="menubar__button"
                    :class="{ 'is-active': isActive.italic() }"
                    @click="commands.italic"
                  >
                    <img class="icon" src="../../assets/img/icons/italic.svg" alt />
                  </button>

                  <button
                    class="menubar__button"
                    :class="{ 'is-active': isActive.strike() }"
                    @click="commands.strike"
                  >
                    <img class="icon" src="../../assets/img/icons/strike.svg" alt />
                  </button>

                  <button
                    class="menubar__button"
                    :class="{ 'is-active': isActive.underline() }"
                    @click="commands.underline"
                  >
                    <img class="icon" src="../../assets/img/icons/underline.svg" alt />
                  </button>

                  <button
                    class="menubar__button"
                    :class="{ 'is-active': isActive.code() }"
                    @click="commands.code"
                  >
                    <img class="icon" src="../../assets/img/icons/code.svg" alt />
                  </button>

                  <button
                    class="menubar__button"
                    :class="{ 'is-active': isActive.paragraph() }"
                    @click="commands.paragraph"
                  >
                    <img class="icon" src="../../assets/img/icons/paragraph.svg" alt />
                  </button>

                  <button
                    class="menubar__button"
                    :class="{ 'is-active': isActive.heading({ level: 1 }) }"
                    @click="commands.heading({ level: 1 })"
                  >H1</button>

                  <button
                    class="menubar__button"
                    :class="{ 'is-active': isActive.heading({ level: 2 }) }"
                    @click="commands.heading({ level: 2 })"
                  >H2</button>

                  <button
                    class="menubar__button"
                    :class="{ 'is-active': isActive.heading({ level: 3 }) }"
                    @click="commands.heading({ level: 3 })"
                  >H3</button>

                  <button
                    class="menubar__button"
                    :class="{ 'is-active': isActive.bullet_list() }"
                    @click="commands.bullet_list"
                  >
                    <img class="icon" src="../../assets/img/icons/ul.svg" alt />
                  </button>

                  <button
                    class="menubar__button"
                    :class="{ 'is-active': isActive.ordered_list() }"
                    @click="commands.ordered_list"
                  >
                    <img class="icon" src="../../assets/img/icons/ol.svg" alt />
                  </button>

                  <button
                    class="menubar__button"
                    :class="{ 'is-active': isActive.blockquote() }"
                    @click="commands.blockquote"
                  >
                    <img class="icon" src="../../assets/img/icons/quote.svg" alt />
                  </button>

                  <button
                    class="menubar__button"
                    :class="{ 'is-active': isActive.code_block() }"
                    @click="commands.code_block"
                  >
                    <img class="icon" src="../../assets/img/icons/code.svg" alt />
                  </button>

                  <button class="menubar__button" @click="commands.horizontal_rule">
                    <img class="icon" src="../../assets/img/icons/hr.svg" alt />
                  </button>

                  <button class="menubar__button" @click="commands.undo">
                    <img class="icon" src="../../assets/img/icons/undo.svg" alt />
                  </button>

                  <button class="menubar__button" @click="commands.redo">
                    <img class="icon" src="../../assets/img/icons/redo.svg" alt />
                  </button>

                  <button class="menubar__button" @click="showImagePrompt(commands.image)">
                    <img class="icon" src="../../assets/img/icons/image.svg" alt />
                  </button>
                </div>
              </editor-menu-bar>
            </div>
            <!-- content -->
            <editor-content class="editor__content" :editor="editor" />
            <!-- tag -->
            <div>
              <tags-input v-model="tag" labelStyle=""  placeholder="해시태그 입력" ref='tags'></tags-input>
            </div>
          </div>
          <!---->
        </div>
        <!---->
      </div>
      <!---->
      <!---->
      <!---->
      <!-- <hr class="foot" /> -->
      <div class="comment_titleNum">
        <img class="comment_icon" src="../../assets/img/comment.png" />
        <h3 class="comment_title">댓글</h3>
        <strong class="num">{{replyCount}}</strong>
      </div>
      <router-link :to="'/'">
        <button class="button btn_list">목록</button>
      </router-link>
      <div class="register_box">
        <div class="checkbox" v-show="this.nickname===this.$store.state.nickname">
          <input type="checkbox" id="editable" v-model="editable" />
          <label for="editable">수정</label>
          <button
            class="button btn_modify"
            v-show="this.nickname===this.$store.state.nickname"
            @click="updateHandler"
          >수정완료</button>
          <button
            class="button btn_delete"
            v-show="this.nickname===this.$store.state.nickname"
            @click="deleteHandler"
          >삭제</button>
           <button 
            class="button btn_modify changeStatus"
            v-show="this.nickname===this.$store.state.nickname && this.status==0"
            @click="changeStatusHandler"
          >저장으로</button>
        </div>
      </div>
      <!-- <hr /> -->
      <div class="ReplyBox">
        <div class="box_left">
          <div class="like_article">
            <!---->
            <!---->
          </div>
          <!-- <a href="#" role="button" aria-pressed="true" class="button_comment">
            <span class="icon_area"></span>
            댓글
            <strong class="num">{{replyCount}}</strong>
          </a>-->
        </div>
      </div>

      <!-- 댓글 파트 -->
      <div class="CommentBox">
        <div class="comment_option">
          <div class="comment_tab">
            <ul class="comment_tab_list">
              <!-- <li class="comment_tab_item">
                <a href="#" role="button" aria-selected="true" class="comment_tab_button">등록순</a>
              </li>
              <li class="comment_tab_item">
                <a href="#" role="button" aria-selected="false" class="comment_tab_button">최신순</a>
              </li>-->
            </ul>
            <button type="button" class="comment_refresh_button">
              <span class="blind">새로고침</span>
            </button>
          </div>
        </div>
        <!-- comment start -->
        <ul class="comment_list">
          <li
            id
            :class="{CommentItem:true,CommentItemReply:reply.parent!==0}"
            v-for="(reply,index) in replys"
            :key="index"
          >
            <div class="comment_area">
              <div class="comment_box">
                <hr />
                <div class="comment_nick_box">
                  <div class="comment_nick_info">
                    <p
                      id
                      href="#"
                      role="button"
                      aria-haspopup="true"
                      aria-expanded="false"
                      class="comment_nickname"
                    >{{reply.nickname}}</p>
                    <!---->
                  </div>
                  <!---->
                </div>
                <div class="comment_text_box">
                  <p class="comment_text_view">
                    <!---->
                    <span class="text_comment">{{reply.content}}</span>
                  </p>
                  <!---->
                </div>
                <div class="comment_info_box">
                  <p
                    class="comment_info_date"
                  >{{$moment(reply.createDate).format('YYYY.MM.DD HH:MM')}}</p>
                  <a class="comment_info_button" @click="openComment(index,reply.replyId)">답글보기</a>
                </div>
                <div class="comment_tool">
                  <!-- <button @click="updateReply">수정</button> -->
                  <button @click="deleteReply(reply.replyId)" v-show="reply.nickname===loggedID">삭제</button>
                  <!---->
                </div>
                <!-- 대댓글 영역 -->
                <div v-if="commentBoxs[index]">
                  <!-- 대댓글 내역 -->
                  <ul class="comment_list">
                    <li
                      id
                      :class="{CommentItem:true,CommentItemReply:reply.parent!==0}"
                      v-for="(rereply,index_re) in rereplys"
                      :key="index_re"
                    >
                      <div class="comment_area">
                        <div class="comment_box">
                          <hr />
                          <div class="comment_nick_box">
                            <div class="comment_nick_info">
                              <p
                                id
                                href="#"
                                role="button"
                                aria-haspopup="true"
                                aria-expanded="false"
                                class="comment_nickname"
                              >{{rereply.nickname}}</p>
                              <!---->
                            </div>
                            <!---->
                          </div>
                          <div class="comment_text_box">
                            <p class="comment_text_view">
                              <!---->
                              <span class="text_comment">{{rereply.content}}</span>
                            </p>
                            <!---->
                          </div>
                          <div class="comment_info_box">
                            <p
                              class="comment_info_date"
                            >{{$moment(rereply.createDate).format('YYYY.MM.DD HH:MM')}}</p>
                          </div>
                          <div class="comment_tool">
                            <!-- <button @click="updateReply">수정</button> -->
                            <button
                              @click="deleteReply(rereply.replyId)"
                              v-show="rereply.nickname===loggedID"
                            >삭제</button>
                            <!---->
                          </div>
                        </div>
                      </div>
                    </li>
                  </ul>
                  <!-- 대댓글 입력 -->
                  <div class="CommentWriter">
                    <div class="comment_inbox">
                      <strong class="blind">댓글을 입력하세요</strong>
                      <textarea
                        id ="rereplyTextArea"
                        :value="replyContent"
                        placeholder="댓글을 남겨보세요"
                        rows="1"
                        class="comment_inbox_text"
                        style="overflow: hidden; overflow-wrap: break-word; height: 18px;"
                      ></textarea>
                      <!---->
                      <!---->
                    </div>
                    <div class="comment_attach">
                      <div class="attach_box">
                        <a href="#" role="button" class="button_sticker">
                          <svg aria-hidden="true" class="svg-icon ico-post-sticker-323232">
                            <use xlink:href="#ico-post-sticker-323232" />
                          </svg>
                        </a>
                        <!---->
                      </div>
                      <div class="register_box">
                        <!---->
                        <a
                          href="#"
                          role="button"
                          class="button btn_register"
                          @click="replyHandler(reply.replyId)"
                        >등록</a>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </li>
          <!---->
          <!---->
        </ul>
        <!-- comment end -->
        <!-- comment register -->
        <div class="CommentWriter">
          <div class="comment_inbox">
            <strong class="blind">댓글을 입력하세요</strong>
            <em
              class="comment_inbox_name"
              v-if="this.$store.state.isLogged"
            >{{this.$store.state.nickname}}</em>
            <!-- <em class="comment_inbox_name" v-else>익명</em> -->
            <textarea
              id ="replyTextArea"
              :value="replyContent"
              placeholder="댓글을 남겨보세요"
              rows="1"
              class="comment_inbox_text"
              style="overflow: hidden; overflow-wrap: break-word; height: 18px;"
            ></textarea>
            <!---->
            <!---->
          </div>
          <div class="comment_attach">
            <div class="attach_box">
              <a href="#" role="button" class="button_sticker">
                <svg aria-hidden="true" class="svg-icon ico-post-sticker-323232">
                  <use xlink:href="#ico-post-sticker-323232" />
                </svg>
              </a>
              <!---->
            </div>
            <div class="register_box">
              <!---->
              <a href="#" role="button" class="button btn_register" @click="replyHandler(0)">등록</a>
            </div>
          </div>
        </div>
        <div style="height: 15px"></div>
        <!---->
      </div>
      <!---->
    </div>
    <div class="empty" style="height: 100px"></div>
  </div>
</template>

<script>
import "../../assets/css/post.scss";
import "../../assets/css/board.css";
import axios from "axios";
import constants from "../../lib/constants";
import Iframe from "./Iframe.js";
import { Editor, EditorContent, EditorMenuBar } from "tiptap";
import {
  Blockquote,
  CodeBlock,
  HardBreak,
  Heading,
  HorizontalRule,
  OrderedList,
  BulletList,
  ListItem,
  TodoItem,
  TodoList,
  Bold,
  Code,
  Italic,
  Link,
  Strike,
  Underline,
  History,
  TrailingNode,
  Image,
} from "tiptap-extensions";
import tagsInput from "../../components/common/TagsInput";
export default {
  components: {
    EditorContent,
    EditorMenuBar,
    tagsInput,
  },
  data() {
    return {
      loggedID: this.$store.state.nickname,
      boardId: "",
      subject: "",
      boardContent: "",
      nickname: "",
      createDate: "",
      views: "",
      countLike: "",
      replyCount: "",
      replyContent: "",
      url: "",
      imgSrc: "",
      tag: "",
      status:"",
      tags: [],
      replys: [],
      rereplys: [],
      commentBoxs: [],
      editable: false,
      editor: new Editor({
        editable: false,
        extensions: [
          new Image(),
          new Blockquote(),
          new BulletList(),
          new CodeBlock(),
          new HardBreak(),
          new Heading({ levels: [1, 2, 3] }),
          new HorizontalRule(),
          new ListItem(),
          new OrderedList(),
          new TodoItem(),
          new TodoList(),
          new Link(),
          new Bold(),
          new Code(),
          new Italic(),
          new Strike(),
          new Underline(),
          new History(),
          new TrailingNode(),
          // custom extension
          new Iframe(),
        ],
        onUpdate: ({ getHTML }) => {
          this.html = getHTML();
          if (this.html === "<p></p>") this.boardContent = "";
          else this.boardContent = this.html;
        },
      }),
    };
  },
  created() {
    const params = new URL(document.location).searchParams;
    this.boardId = params.get("boardid");
    axios
      .get(constants.baseUrl + `/board/${params.get("boardid")}`)
      .then(({ data }) => {
        //console.log(data);
        this.subject = data.subject;
        this.editor.setContent(data.content);
        this.boardContent = data.content;
        this.nickname = data.nickname;
        this.createDate = data.createDate;
        this.status=data.status;
        this.views = data.views;
        this.countLike = data.countLike;
        this.replyCount = data.replyCount;
        this.replys = data.replys;
        this.imgSrc = data.imgSrc;
        this.$refs.tags.setTags(data.tags);
        this.tags = data.tags;
        this.$refs.tags.toggleEditable();
        this.commentBoxs = new Array(data.replyCount).fill(false);
      });
  },
  watch: {
    editable() {
      this.editor.setOptions({
        editable: this.editable,
      });
      this.$refs.tags.toggleEditable();
    },

  },
  computed: {
      date(){
        var date = this.createDate.slice(0,10);
        var time = this.createDate.slice(11);
        return date + " "+ time;
      }
  },
  methods: {
    showImagePrompt(command) {
      const src = prompt("Enter the url of your image here");
      if (src !== null) {
        command({ src });
      }
    },
    getEmbedSrc() {
      let tmpSrc = document.getElementById("iframe__embed").src;
      this.url = tmpSrc.substring(tmpSrc.length - 11, tmpSrc.length);
      // console.log(this.url);
    },
    //임시저장 -> 저장 
    changeStatusHandler(){
       this.status=1;
       this.updateHandler();
    },
    // 작성 글 변경
    updateHandler() {
      this.getEmbedSrc();
      // console.log(this.$store.state.nickname);
      axios
        .put(
          constants.baseUrl + "/board",
          {
            boardId: this.boardId,
            subject: this.subject,
            content: this.boardContent,
            status: this.status,
            url: this.url,
            tags: this.$refs.tags.getTags(),
          },
          { headers: { accesstoken: this.$store.state.accesstoken } }
        )
        .then(({ data }) => {
          // console.log(data)
          this.moveList();
        })
        .catch(function (error) {
          if (error.response) {
            // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            // 요청이 이루어 졌으나 응답을 받지 못했습니다.
            // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
            // Node.js의 http.ClientRequest 인스턴스입니다.
            console.log(error.request);
          } else {
            // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
            console.log("Error", error.message);
          }
          console.log(error.config);
        });
    },
    // 작성 글 삭제
    deleteHandler() {
      axios
        .delete(constants.baseUrl + `/board/${this.boardId}`, {
          headers: { accesstoken: this.$store.state.accesstoken },
        })
        .then(({ data }) => {
          this.moveList();
        })
        .catch(function (error) {
          if (error.response) {
            // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            // 요청이 이루어 졌으나 응답을 받지 못했습니다.
            // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
            // Node.js의 http.ClientRequest 인스턴스입니다.
            console.log(error.request);
          } else {
            // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
            console.log("Error", error.message);
          }
          console.log(error.config);
        });
    },
    moveList() {
      this.$router.push("/list");
    },
    // 댓글 작성
    replyHandler(data) {
      if(this.$store.state.nickname=='') {
        alert("해당서비스는 로그인이 필요한 서비스입니다!")
        return;
        }
      var textarea = '';
      if(data==0){
      textarea = document.getElementById('replyTextArea');
      }else{
       textarea = document.getElementById('rereplyTextArea');
      }
      axios
        .post(
          constants.baseUrl + "/reply",
          {
            boardId: this.boardId,
            nickname: this.$store.state.nickname,
            parent: data,
            seq: 0,
            content: textarea.value,
          },
          { headers: { accesstoken: this.$store.state.accesstoken } }
        )
        .then(({ data }) => {
          // console.dir(data)
          alert("댓글이 작성되었습니다.");
          window.location.reload();
     
          
        })
        .catch(function (error) {
          if (error.response) {
            // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            // 요청이 이루어 졌으나 응답을 받지 못했습니다.
            // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
            // Node.js의 http.ClientRequest 인스턴스입니다.
            console.log(error.request);
          } else {
            // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
            console.log("Error", error.message);
          }
          console.log(error.config);
        });
    },
    // 댓글 삭제
    deleteReply(id) {
      axios
        .delete(constants.baseUrl + "/reply/" + id, {
          headers: { accesstoken: this.$store.state.accesstoken },
        })
        .then(({ data }) => {
          alert("댓글이 삭제되었습니다.");
          window.location.reload();
        })
        .catch(function (error) {
          if (error.response) {
            // 요청이 이루어졌으며 서버가 2xx의 범위를 벗어나는 상태 코드로 응답했습니다.
            console.log(error.response.data);
            console.log(error.response.status);
            console.log(error.response.headers);
          } else if (error.request) {
            // 요청이 이루어 졌으나 응답을 받지 못했습니다.
            // `error.request`는 브라우저의 XMLHttpRequest 인스턴스 또는
            // Node.js의 http.ClientRequest 인스턴스입니다.
            console.log(error.request);
          } else {
            // 오류를 발생시킨 요청을 설정하는 중에 문제가 발생했습니다.
            console.log("Error", error.message);
          }
          console.log(error.config);
        });
    },
    // 대댓글 달 수 있는 댓글창 보이기
    openComment(index, id) {
      if (this.commentBoxs[index]) {
        this.commentBoxs.splice(index, 1, false);
      } else {
        this.commentBoxs.splice(index, 1, true);
        axios.get(constants.baseUrl + "/reply/re/" + id).then(({ data }) => {
          this.rereplys = data;
        });
      }
    },
  },
};
</script>

<style lang="scss" scoped>
.changeStatus{
  font-family: 'NEXON Lv1 Gothic OTF';
    font-size: 16px;
    padding-top: 9px;
    width:100px;
    height: 40px;
    margin-top: 70px;
    margin-left: 10px;

  

}
.editor__content{
  margin-bottom: 15px;
}
.icon {
  position: relative;
  display: inline-block;
  vertical-align: middle;
  width: 0.8rem;
  height: 0.8rem;
  margin: 0 0.3rem;
  top: -0.05rem;
  fill: currentColor;

  // &.has-align-fix {
  // 	top: -.1rem;
  // }

  &__svg {
    display: inline-block;
    vertical-align: top;
    width: 100%;
    height: 100%;
  }

  &:first-child {
    margin-left: 0;
  }

  &:last-child {
    margin-right: 0;
  }
}

@font-face {
  font-family: "NEXON Lv1 Gothic OTF";
  src: url("https://cdn.jsdelivr.net/gh/projectnoonnu/noonfonts_20-04@2.1/NEXON Lv1 Gothic OTF.woff")
    format("woff");
  font-weight: normal;
  font-style: normal;
}
</style>