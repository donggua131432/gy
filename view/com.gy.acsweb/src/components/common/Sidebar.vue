<template>
  <div class="sidebar">
    <el-menu
      class="sidebar-el-menu"
      :default-active="onRoutes"
      :collapse="false"
      background-color="#324157"
      text-color="#bfcbd9"
      active-text-color="#20a0ff"
      unique-opened
      :default-openeds="defShowMenu"
      router>
      <template v-for="menu in menuList">
        <template v-if="menu.children && menu.children.length>0">
          <el-submenu :index="menu.menuId">
            <template slot="title">
              <i :class="menu.menuIco"></i><span slot="title">{{ menu.menuName }}</span>
            </template>
            <template v-for="subMenu in menu.children">
              <el-submenu
                v-if="subMenu.children && subMenu.children.length>0"
                :index="subMenu.menuId"
              >
                <template slot="title">
                  <i :class="subMenu.menuIco"></i><span slot="title">{{ subMenu.menuName }}</span>
                </template>
                <el-menu-item
                  v-for="threeMenu in subMenu.children"
                  :key="threeMenu.url"
                  :index="threeMenu.url"
                >
                  <i :class="threeMenu.menuIco"></i><span slot="title">{{ threeMenu.menuName }}</span>
                </el-menu-item>
              </el-submenu>
              <el-menu-item
                v-else
                :index="subMenu.url"
              >
                <i :class="subMenu.menuIco"></i><span slot="title">{{ subMenu.menuName }}</span>
              </el-menu-item>
            </template>
          </el-submenu>
        </template>
        <template v-else>
          <el-menu-item :index="menu.url">
            <i :class="menu.menuIco"></i><span slot="title">{{ menu.menuName }}</span>
          </el-menu-item>
        </template>
      </template>
    </el-menu>
  </div>
</template>

<script>
import bus from "../common/bus";
export default {
  data() {
    return {
      menuList: [],
      defShowMenu: []
    };
  },
  computed: {
    onRoutes() {
      return this.$route.path.replace("/", "");
    }
  },
  created() {
    this.$util.get('/menu/listAllMenu').then(result => {
      this.menuList = result.data.data;
      this.defShowMenu = [this.menuList[2].menuId]
    });

    // 通过 Event Bus 进行组件间通信，来折叠侧边栏
    bus.$on("collapse", msg => {
      this.collapse = msg;
    });
  }
};
</script>

<style scoped>
.sidebar {
  display: block;
  position: absolute;
  left: 0;
  top: 70px;
  bottom: 0;
  overflow-y: scroll;
}
.sidebar::-webkit-scrollbar {
  width: 0;
}
.sidebar-el-menu:not(.el-menu--collapse) {
  width: 250px;
}
.sidebar > ul {
  height: 100%;
}
</style>
