import { localAxios } from "@/util/http-commons";

const local = localAxios();

const url = "/user";
async function userConfirm(param, success, fail) {
  console.log("param", param);
  await local.post(`/user/login`, JSON.stringify(param)).then(success).catch(fail);
  console.log("userConfirm ok");
}

async function findById(userid, success, fail) {
  local.defaults.headers["Authorization"] = sessionStorage.getItem("accessToken");
  await local.get(`/${url}/info/${userid}`).then(success).catch(fail);
}

async function tokenRegeneration(user, success, fail) {
  local.defaults.headers["refreshToken"] = sessionStorage.getItem("refreshToken"); //axios header에 refresh-token 셋팅
  await local.post(`/${url}/refresh`, user).then(success).catch(fail);
}

async function logout(userid, success, fail) {
  await local.get(`/${url}/logout/${userid}`).then(success).catch(fail);
}

function joinMember(member, success, fail) {
  console.log("member.js member", member);
  local.post(`${url}/register`, JSON.stringify(member)).then(success).catch(fail);
}
function updateMember(member, success, fail) {
  local.put(`${url}`, JSON.stringify(member)).then(success).catch(fail);
}

function deleteMember(userId, commentNo, success, fail) {
  local.delete(`${url}/${userId}`).then(success).catch(fail);
}

export { userConfirm, findById, tokenRegeneration, logout, joinMember, updateMember, deleteMember };
