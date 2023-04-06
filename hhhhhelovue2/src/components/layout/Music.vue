<!-- eslint-disable vue/multi-word-component-names -->
<template>
  <aplayer autoplay :audio="audio" fixed />
</template>

<script>
export default {
  data() {
    return {
      audio: [
        {
          name: "Viva La Vida",
          artist: "Coldplay",
          url: "https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/06/a2c9fe7e82754cb590e33fec466c065e.mp3",
          cover:
            "https://p2.music.126.net/Wr13D68yaaknFmxTD5xnoQ==/18569651881855121.jpg?param=130y130",
        },
        {
          name: "三葉のテーマ",
          artist: "RADWIMPS",
          url: "https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/05/b601cb18135648ecb3e3a7cc44ebbbd0.mp3",
          cover:
            "https://p1.music.126.net/sSxbRt9RpC6s_MaewyDJfA==/18597139672292692.jpg?param=130y130",
        },
        {
          name: "Another Place",
          artist: "Duumu",
          url: "https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/04/039d226b53c64fd9a08faa63899aaf39.mp3",
          cover: 'https://p1.music.126.net/IxlduAs-xNLmD3HOHBw6eQ==/18550960185770485.jpg?param=130y130', // prettier-ignore
        },
        {
          name: "美好的事可不可以發生在我身上",
          artist: "康士坦的变化球",
          url: "https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/04/cce1dcb370e6486081e1e939b093375a.mp3",
          cover: 'https://p2.music.126.net/8sAQGYvrvOZqvNdB9Heb1Q==/109951166270738744.jpg?param=130y130', // prettier-ignore
        },
        {
          name: "Yellow ",
          artist: "Coldplay",
          url: "https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/04/9ab00676d4c048de875812c4e11f2e4c.mp3",
          cover: 'https://p2.music.126.net/n6BatGZdnRaEnIC0h7kVOg==/109951167815599264.jpg?param=130y130', // prettier-ignore
        },
        {
          name: "玩具",
          artist: "岛屿心情",
          url: "https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/04/0cf3d3f9db5a43cc855dc7e49b064b05.mp3",
          cover: 'https://p2.music.126.net/KHqGDy5zFlQb5WOQTBNhjg==/18150737951738895.jpg?param=130y130', // prettier-ignore
        },
        {
          name: "eau",
          artist: "yutaka hirasaka",
          url: "https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/04/4eaf54bc27794386b17046003fbcfaff.mp3",
          cover: 'https://p1.music.126.net/tFQPUKGe5siIKCR_4j7Bww==/109951165777937342.jpg?param=130y130', // prettier-ignore
        },
        {
          name: "寂寞先生",
          artist: "马英伦",
          url: "https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/04/80f4210da95444a195cd88b7a10e16b7.mp3",
          cover: 'https://p2.music.126.net/-KtWxXMzdSPVj7IFhjP-Ug==/109951163054435928.jpg?param=130y130', // prettier-ignore
        },
        {
          name: "Gotta Have You",
          artist: "The Weepies",
          url: "https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/05/0842e1a0ba5c4b728fcebce0a12080d0.mp3",
          cover:
            "https://p1.music.126.net/EUF1CK9GOrtT0ykWrv5jkQ==/109951167031383017.jpg?param=130y130",
        },
        {
          name: "最美好的时光",
          artist: "陈娴静",
          url: "https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/05/8f476ad6f9aa4969abe4c218e246e024.mp3",
          cover:
            "https://p1.music.126.net/cHyK0Yxcdv1aI0Z7m7qA_A==/109951167728688180.jpg?param=130y130  ",
        },
        {
          name: "The Moments We Shared...I Miss Them",
          artist: "Jacoo",
          url: "https://xitong-parent.oss-cn-hangzhou.aliyuncs.com/2022/09/05/5155e2e0fc664bfa9ea1f09065cb2637.mp3",
          cover:
            "https://p1.music.126.net/BoG7QiDR9Ws6TlK68kCyfQ==/7700979441482813.jpg?param=130y130",
        },
      ],
    };
  },
};
</script>