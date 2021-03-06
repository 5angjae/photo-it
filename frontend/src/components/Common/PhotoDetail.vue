<template>
  <div
    class="modal fade"
    :id="`detailModal-${detailPhotoId}`"
    tabindex="-1"
    aria-labelledby="detailModalLabel"
    aria-hidden="true"
  >
    <div class="modal-dialog">
      <div class="modal-content">
        <div class="modal-body d-flex justify-content-center">
          <img :src="detailPhoto" alt="detail" class="modal-img" />
          <button class="origin-show px-3 py-2" @click="originShow">
            <div class="mb-1 mt-1"><i class="fas fa-images fa-3x"></i></div>
            <div>원본 보기</div>
          </button>
        </div>
        <div class="modal-footer d-flex justify-content-start pt-0">
          <div class="ps-2 pb-2" style="color: white">
            <p class="mb-0">
              <!-- 작가 프로필 사진 및 찜 아이콘 -->
              <span class="pe-3 profile-box">
                <span v-if="detailProfile">
                  <img
                    class="pg-profile-photo"
                    :src="detailProfile"
                    alt="photographer profile"
                  />
                  <i
                    v-if="detailIsFavorite && isLogin"
                    class="fas fa-heart favorit-icon"
                    @click="deleteFavorite"
                  ></i>
                  <i
                    v-else-if="!detailIsFavorite && isLogin"
                    class="far fa-heart favorit-icon"
                    @click="addFavorite"
                  ></i>
                </span>
                <!-- 기본 프로필 사진 및 찜 아이콘 -->
                <span v-else>
                  <img
                    class="pg-profile-photo"
                    src="../../assets/images/profile_default.png"
                    alt="default profile"
                  />
                  <i
                    v-if="detailIsFavorite && isLogin"
                    class="fas fa-heart favorit-icon"
                    @click="deleteFavorite"
                  ></i>
                  <i
                    v-else-if="!detailIsFavorite && isLogin"
                    class="far fa-heart favorit-icon"
                    @click="addFavorite"
                  ></i>
                </span>
              </span>
              <!-- 사진 작가 닉네임 -->
              <router-link
                data-bs-dismiss="modal"
                class="go-mystudio"
                :to="{ path: `/mystudio/${detailPgNickname}` }"
              >
                <span class="me-5 fw-bold">
                  {{ detailPgNickname }}
                </span>
              </router-link>
              <!-- 사진 태그 리스트 -->
              <span v-for="(tag, idx) in detailTagList" :key="idx" class="me-2">
                #{{ tag }}
              </span>
            </p>
          </div>
          <!-- 작가의 다른 사진 -->
          <div class="container-fluid px-0">
            <div class="row">
              <div
                class="col-3"
                v-for="(item, idx) in detailOtherPhotos"
                :key="idx"
              >
                <div class="card">
                  <img
                    class="img-fluid card-img"
                    alt="other photo"
                    :src="item.thumbnail"
                    :id="`id-${item.photoId}`"
                    @click="photoDetail(detailPgNickname, item.thumbnail)"
                  />
                  <PhotoDetail v-if="modalToggle" />
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'PhotoDetail',
  data() {
    return {
      modalToggle: false,
    }
  },
  computed: {
    detailPhoto() {
      return this.$store.state.mainpage.detailPhoto
    },
    detailPhotoId() {
      return this.$store.state.mainpage.detailPhotoId
    },
    detailOtherPhotos() {
      return this.$store.state.mainpage.detailOtherPhotos
    },
    detailPgNickname() {
      return this.$store.state.mainpage.detailPgNickname
    },
    detailTagList() {
      return this.$store.state.mainpage.detailTagList
    },
    detailProfile() {
      return this.$store.state.mainpage.detailProfile
    },
    detailIsFavorite() {
      return this.$store.state.mainpage.detailIsFavorite
    },
    isLogin() {
      return this.$store.state.login.isLogin
    },
  },
  methods: {
    async photoDetail(itemNickname, itemThumbnail) {
      this.modalToggle = false
      if (this.modalToggle === false) {
        let id = ''
        this.$store.dispatch('login/isLoginCheck')
        if (this.$store.state.login.isLogin) {
          id = localStorage.getItem('id')
        }
        const nickName = itemNickname
        const thumbnail = itemThumbnail
        await this.$store.dispatch('mainpage/getDetailPhotos', {
          id: id,
          nickName: nickName,
          thumbnail: thumbnail,
        })
        this.modalToggle = true
      }
    },
    addFavorite() {
      this.$store.dispatch('mainpage/addFavorite', {
        pgNick: this.detailPgNickname,
        userNick: this.$store.state.login.myNickname,
      })
    },
    deleteFavorite() {
      this.$store.dispatch('mainpage/deleteFavorite', {
        pgNick: this.detailPgNickname,
        userNick: this.$store.state.login.myNickname,
      })
    },
    originShow() {
      window.open(this.detailPhoto)
    },
  },
  created() {
    // 로그인 여부 확인
    this.$store.dispatch('login/isLoginCheck')
    this.$store.dispatch('login/getNickname')
  },
}
</script>

<style scoped>
.modal-img {
  max-width: 40vw;
  max-height: 50vh;
  object-fit: contain;
}
.modal-dialog {
  max-width: 45vw;
  max-height: 50vh;
}
/* .modal-body {
  margin: 0.25rem;
  padding: 0;
} */
.modal-content {
  background-color: black;
}
.modal-footer {
  border-style: none;
}
.card-img {
  width: 100%;
  height: 10vh;
  object-fit: cover;
}
.card-img:hover {
  cursor: pointer;
  transform: scale(1.1);
  opacity: 0.7;
  overflow: hidden;
}
.card {
  border-style: none;
}
.pg-profile-photo {
  width: 5vh;
  height: 5vh;
  border-radius: 50%;
  overflow: hidden;
  object-fit: cover;
}
.profile-box {
  position: relative;
}
.favorit-icon {
  position: absolute;
  top: 100%;
  right: 20%;
  color: red;
  cursor: pointer;
}
.go-mystudio {
  text-decoration: none;
  color: white;
}
.go-mystudio:hover {
  text-decoration: underline;
}
.origin-show {
  visibility: hidden;
  position: absolute;
  top: 4%;
  left: 3%;
  border-radius: 1rem;
  /* opacity: 90%; */
  border: 0.3rem rgba(136, 136, 136, 0.507);
  border-style: none solid solid none;
  background-color: white;
  outline: none;
  transition: all 0.3s ease 0s;
  font-size: 0.8rem;
}
.modal-body:hover .origin-show {
  visibility: visible;
  transform: translateY(10px);
}
</style>
