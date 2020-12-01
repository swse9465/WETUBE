<template>
  <div id="writeBoard">
    <div class="writeHeader">
      <img src="../../assets/img/writeHeader.jpg" />
      <div class="writeHeader-text">
        <p>자신만의 리뷰를 작성하여 다른 사람들과 공유해보세요</p>
      </div>
    </div>
    <div id="editor">
      <div class="editor">
        <Modal ref="ytmodal" @onConfirm="addCommand" />
        <div class="board__name">
          <input
            type="text"
            class="form-control"
            id="subject"
            ref="subject"
            placeholder="제목을 입력하세요"
            v-model="subject"
          />
        </div>
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

            <button class="menubar__button" @click="openModal(commands.iframe);">
              <svg viewBox="0 0 511.6 511.6" width="14" height="14">
                <path
                  d="M511.3 213c-.1-10.3-1-23.3-2.4-39a354.4 354.4 0 0 0-6.1-42.1 66.4 66.4 0 0 0-19.9-35.1c-10.1-9.5-22-15-35.5-16.6-42.3-4.7-106.1-7.1-191.6-7.1-85.4 0-149.3 2.4-191.6 7.1a60.2 60.2 0 0 0-35.4 16.6 66.8 66.8 0 0 0-19.7 35.1A316 316 0 0 0 2.7 174a560.2 560.2 0 0 0-2.4 39 2430.9 2430.9 0 0 0 0 85.6 560 560 0 0 0 2.4 39c1.4 15.7 3.5 29.8 6.2 42.1a65.4 65.4 0 0 0 55.3 51.7c42.3 4.8 106.1 7.1 191.6 7.1s149.3-2.3 191.6-7.1c13.5-1.5 25.3-7 35.4-16.6a66.8 66.8 0 0 0 19.7-35c2.8-12.4 5-26.5 6.4-42.2 1.4-15.7 2.2-28.7 2.4-39a2450.8 2450.8 0 0 0 0-85.6zM357 271.2l-146.2 91.4a16.3 16.3 0 0 1-9.7 2.8c-2.9 0-5.8-.7-8.9-2.2a17 17 0 0 1-9.4-16V164.5a17 17 0 0 1 9.4-16 17.2 17.2 0 0 1 18.6.5L357 240.4c5.7 3.2 8.5 8.4 8.5 15.4s-2.8 12.2-8.5 15.4z"
                />
              </svg>
            </button>
          </div>
        </editor-menu-bar>
        <div class="content">
          <editor-content class="editor__content" :editor="editor" />
        </div>
        <div>
           <!-- labelStyle="warning"  -->
          <tags-input v-model="tag" labelStyle="" placeholder="해시태그를 추가해주세요" ref="tags"></tags-input>
        </div>
        <div class="text-center">
          <button class="btn btn-regist" @click="createHandler">등록</button>
          <button class="btn btn-temp" @click="saveHandler">임시저장</button>
          <button class="btn btn-cancle" @click="moveList">취소</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "../../assets/css/post.scss";
import Icon from "./Icon";
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
import axios from "axios";
import constants from "../../lib/constants";
import Modal from "./Modal";
import tagsInput from "../../components/common/TagsInput";
export default {
  components: {
    EditorContent,
    EditorMenuBar,
    Modal,
    tagsInput,
  },
  data() {
    return {
      subject: "",
      boardContent: "",
      url: "",
      tag: "",
      editor: new Editor({
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
        content: `
          <br>
        `,
        onUpdate: ({ getHTML }) => {
          this.html = getHTML();
          if (this.html === "<p></p>") this.boardContent = "";
          else this.boardContent = this.html;
        },
      }),
    };
  },
  beforeDestroy() {
    this.editor.destroy();
  },
  mounted() {
    this.setContent();
  },
  methods: {
    openModal(command) {
      this.$refs.ytmodal.showModal(command);
    },
    addCommand(data) {
      if (data.command !== null) {
        data.command(data.data);
      }
    },
    setContent() {
      this.editor.setContent(this.content);
    },
    showImagePrompt(command) {
      const src = prompt("추가할 이미지 URL을 등록해주세요");
      if (src !== null) {
        command({ src });
      }
    },
    getEmbedSrc() {
      let tmpSrc = document.getElementById("iframe__embed").src;
      this.url = tmpSrc.substring(tmpSrc.length - 11, tmpSrc.length);
      // console.log(this.url);
    },
    // 임시저장
    saveHandler() {
      this.getEmbedSrc();
      // console.log(this.$store.state.nickname);
      axios
        .post(
          constants.baseUrl + "/board",
          {
            subject: this.subject,
            content: this.boardContent,
            userId: this.$store.state.userId,
            // 임시저장은 status 0
            status: 0,
            nickname: this.$store.state.nickname,
            url: this.url,
            tags: this.$refs.tags.getTags(),
          },
          { headers: { accesstoken: this.$store.state.accesstoken } }
        )
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
    // 글작성
    createHandler() {
      this.getEmbedSrc();
      console.log(this.$store.state.nickname);
      axios
        .post(
          constants.baseUrl + "/board",
          {
            subject: this.subject,
            content: this.boardContent,
            userId: this.$store.state.userId,
            status: 1,
            nickname: this.$store.state.nickname,
            url: this.url,
            tags: this.$refs.tags.getTags(),
          },
          { headers: { accesstoken: this.$store.state.accesstoken } }
        )
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
  },
};
</script>

<style lang="scss" scoped>
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
    width: 1020%;
    height: 100%;
  }

  &:first-child {
    margin-left: 0;
  }

  &:last-child {
    margin-right: 0;
  }
}

.content {
  overflow: scroll;
  height: 500px;
  border: solid 1px #bfbfbf;
}

.board__name {
  padding-bottom: 40px;
}

.editor {
  max-width: 44rem;
}

.iframe {
  &__embed {
    width: 100%;
    height: 15rem;
    border: 0;
  }
  &__input {
    display: block;
    width: 100%;
    font: inherit;
    border: 0;
    border-radius: 5px;
    background-color: rgba(#000000, 0.1);
    padding: 0.3rem 0.5rem;
  }
}

@import url("https://fonts.googleapis.com/css2?family=Noto+Sans+KR&display=swap");
@import url("https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap");
@import url("https://fonts.googleapis.com/css2?family=Black+Han+Sans&display=swap");
</style>
