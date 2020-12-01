<template>
  <div class="userModal" v-if="visible" @click.self="handleWrapperClick">
    <div class="userModal__dialog">
      <header class="userModal__header">
        <button class="userModal__btn mypage-btn" @click="moveUserInfo">마이페이지</button>
        <button class="userModal__btn temp-btn" @click="moveTempBoard">임시보관함</button>
        <button class="userModal__btn logout-btn" @click="moveLogout">로그아웃 </button>
      </header>
    </div>
  </div>
</template>

<script>
export default {
  name: "userModal",
  props: {
    visible: {
      type: Boolean,
      require: true,
      default: false,
    },
  },
  methods: {
    handleWrapperClick() {
      this.$emit("update:visible", false);
    },
    moveUserInfo() {
        this.$router.push("/user/Inforamtion");
      this.handleWrapperClick();
    },
    moveTempBoard() {
        this.$router.push("/TempBoardList");
      this.handleWrapperClick();
    },
    moveLogout() {
          this.$store.commit("LOGOUT");
      this.handleWrapperClick();
    }
  },
};
</script>
<style lang="scss">
@import url("https://fonts.googleapis.com/css2?family=Short+Stack&display=swap");
$module: "userModal";
.#{$module} {
  // This is modal bg
  top: 0;
  right: 0;
  bottom: 0;
  left: 0;
  position: fixed;
  overflow: auto;
  margin: 0;
  z-index: 1000;
  //This is modal layer
  &__dialog {
    left: 88%;
    top: 10%;
    bottom: 15%;
    width: 160px;
    position: relative;
    background:#ffe6e6;
    border-radius: 5px;
    z-index: 1000;
  }
  &__header {
    font-size: 28px;
    font-weight: bold;
    line-height: 1.29;
    padding: 8px 16px 16px 16px;
    position: relative;
  }
  &__btn {
    width: 100%;
  }
}

.mypage-btn{
  margin-top: 10px;
  margin-bottom: 5px;
}
</style>



