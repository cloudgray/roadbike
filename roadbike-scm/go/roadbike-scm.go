/*
Copyright IBM Corp. 2016 All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

		 http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package main

import (
	"encoding/json"
	"fmt"
	"strconv"
	"time"

	"github.com/hyperledger/fabric-contract-api-go/contractapi"
)

// SmartContract Chaincode implementation
type SmartContract struct {
	contractapi.Contract
}

// Shimano struct
type Shimano struct {
	TrStock []string `json:"tStock"`
	BrStock []string `json:"bStock"`
	WhStock []string `json:"wStock"`
	SN      int      `json:"sn"`
}

// Specialized struct
type Specialized struct {
	TrStock   []string `json:"tStock"`
	BrStock   []string `json:"bStock"`
	WhStock   []string `json:"wStock"`
	BikeStock []string `json:"bikeStock"`
	SN        int      `json:"sn"`
}

// BikeStore struct
type BikeStore struct {
	StoreName string   `json:"storeName"`
	BikeStock []string `json:"bikeStock"`
}

// Roadbike ...
type Roadbike struct {
	SerialNumber      string  `json:"serialNumber"`
	Model             string  `json:"model"`
	Colour            string  `json:"colour"`
	Owner             string  `json:"owner"`
	PlaceOfProduction string  `json:"production"`
	DateOfProduction  string  `json:"productionDate"`
	BikeStore         string  `json:"bikeStore"`
	DateOfWarehousing string  `json:"dateOfWarehousing"`
	DateOfPurchase    string  `json:"dateOfPurchase"`
	TradeHistory      []Trade `json:"tradeHistory"`
	Transmission      string  `json:"transmission"`
	Brake             string  `json:"brake"`
	Wheel             string  `json:"wheel"`
	Price             int     `json:"price"`
}

// Part struct
type Part struct {
	SerialNumber      string `json:"serialNumber"`
	Model             string `json:"model"`
	PlaceOfProduction string `json:"placeofProduction"`
	DateOfProduction  string `json:"dateOfProduction"`
	Price             int    `json:"price"`
}

// Trade struct
type Trade struct {
	BikeSN        string `json:"bikeSN"`
	DateOfOpen    string `json:"date"`
	DateOfSign    string `json:"dateOfSign"`
	Seller        string `json:"seller"`
	Buyer         string `json:"buyer"`
	OriginalPrice int    `json:"originalPrice"`
	LastPrice     int    `json:"lastPrice"`
	CurrentPrice  int    `json:"currentPrice"`
	State         string `json:"state"`
}

func (s *SmartContract) makeTrade(ctx contractapi.TransactionContextInterface, _sn string, _seller string) (Trade, error) {
	timeNow := time.Now().Format("2020-01-01")
	var roadbike Roadbike
	roadbikeBytes, err := ctx.GetStub().GetState(_sn)
	if err != nil {
		return Trade{}, fmt.Errorf("fail to change serialNumber")
	}

	json.Unmarshal(roadbikeBytes, &roadbike)
	price := roadbike.Price
	trade := Trade{BikeSN: _sn, DateOfOpen: timeNow, DateOfSign: "", Seller: _seller, Buyer: "", OriginalPrice: price, LastPrice: price, CurrentPrice: price, State: "Open"}

	if err != nil {
		return Trade{}, fmt.Errorf("fail to change serialNumber")
	}

	var tradeList []Trade

	// Put state of tradeList
	tradeListBytes, _ := ctx.GetStub().GetState("TradeList")
	json.Unmarshal(tradeListBytes, &tradeList)
	tradeList = append(tradeList, trade)
	tradeListBytes, _ = json.Marshal(tradeList)
	err = ctx.GetStub().PutState("TradeList", tradeListBytes)
	if err != nil {
		return Trade{}, fmt.Errorf("fail to change serialNumber")
	}

	// Put state of roadbike
	tradeList = roadbike.TradeHistory
	roadbike.TradeHistory = append(tradeList, trade)
	roadbikeBytes, _ = json.Marshal(roadbikeBytes)
	err = ctx.GetStub().PutState(_sn, roadbikeBytes)
	if err != nil {
		return Trade{}, fmt.Errorf("fail to change serialNumber")
	}

	return trade, nil
}

func signTrade(ctx contractapi.TransactionContextInterface, _sn string, _buyer string) {

}

// Produce func
func (s *SmartContract) producePart(ctx contractapi.TransactionContextInterface, partName string) error {
	var shimano Shimano
	shimanoBytes, _ := ctx.GetStub().GetState("Shimano")
	_ = json.Unmarshal(shimanoBytes, &shimano)
	snInt := shimano.SN + 1
	shimano.SN = snInt
	snStr := ""

	timeNow := time.Now().Format("2020-01-01")
	var part Part
	switch partName {
	case "transmission":
		snStr = "SHTRJP" + strconv.Itoa(snInt)
		part = Part{SerialNumber: snStr, Model: "Transmission_Shimano", PlaceOfProduction: "Factory_Japan", DateOfProduction: timeNow, Price: 500000}
	case "brake":
		snStr = "SHBRJP" + strconv.Itoa(snInt)
		part = Part{SerialNumber: snStr, Model: "Brake_Shimano", PlaceOfProduction: "Factory_Japan", DateOfProduction: timeNow, Price: 200000}
	case "wheel":
		snStr = "SHWHJP" + strconv.Itoa(snInt)
		part = Part{SerialNumber: snStr, Model: "Wheel_Shimano", PlaceOfProduction: "Factory_Japan", DateOfProduction: timeNow, Price: 300000}
	}

	partBytes, _ := json.Marshal(part)
	err := ctx.GetStub().PutState(part.SerialNumber, partBytes)
	if err != nil {
		return fmt.Errorf("fail to produce")
	}

	shimanoBytes, _ = json.Marshal(shimano)
	err = ctx.GetStub().PutState("Shimano", shimanoBytes)
	if err != nil {
		return fmt.Errorf("fail to change serialNumber")
	}

	return nil
}

func pop(arr []string) (string, []string) {
	rtn := arr[0]
	copy(arr[0:], arr[1:])
	arr = arr[:len(arr)-1]
	return rtn, arr
}

// purchasePart func
func (s *SmartContract) purchasePart(ctx contractapi.TransactionContextInterface, partName string) error {

	// shimano state
	var shimano Shimano
	shimanoBytes, _ := ctx.GetStub().GetState("Shimano")
	err := json.Unmarshal(shimanoBytes, &shimano)
	if err != nil {
		return fmt.Errorf("Failed to read world state. %s", err.Error())
	}

	// specaialized state
	var specialized Specialized
	specializedBytes, _ := ctx.GetStub().GetState("Specialized")
	err = json.Unmarshal(specializedBytes, &specialized)
	if err != nil {
		return fmt.Errorf("Failed to read world state. %s", err.Error())
	}

	sn := ""
	switch partName {
	case "transmission":
		sn, shimano.TrStock = pop(shimano.TrStock)
		specialized.TrStock = append(specialized.TrStock, sn)
	case "brake":
		sn, shimano.BrStock = pop(shimano.BrStock)
		specialized.BrStock = append(specialized.BrStock, sn)
	case "wheel":
		sn, shimano.WhStock = pop(shimano.WhStock)
		specialized.WhStock = append(specialized.WhStock, sn)
	}

	// part state
	var part Part
	partBytes, _ := ctx.GetStub().GetState(sn)
	err = json.Unmarshal(partBytes, &part)
	if err != nil {
		return fmt.Errorf("Failed to read world state. %s", err.Error())
	}

	specializedBytes, _ = json.Marshal(specialized)
	_ = ctx.GetStub().PutState("Specialized", specializedBytes)

	shimanoBytes, _ = json.Marshal(shimano)
	_ = ctx.GetStub().PutState("Shimano", shimanoBytes)

	return nil
}

// ProduceRoadbike func
func (s *SmartContract) produceRoadbike(ctx contractapi.TransactionContextInterface) (string, error) {
	fmt.Println("GenerateRoadbike !")
	timeNow := time.Now().Format("2020-01-01")

	// specaialized state
	var specialized Specialized
	specializedBytes, _ := ctx.GetStub().GetState("Specialized")
	err := json.Unmarshal(specializedBytes, &specialized)
	if err != nil {
		return "", fmt.Errorf("Failed to read world state. %s", err.Error())
	}
	snInt := specialized.SN + 1
	specialized.SN = snInt

	snTr := ""
	snBr := ""
	snWh := ""
	snTr, specialized.TrStock = pop(specialized.TrStock)
	snBr, specialized.BrStock = pop(specialized.TrStock)
	snWh, specialized.WhStock = pop(specialized.TrStock)

	tradeHistory := []Trade{}

	roadbike := Roadbike{
		SerialNumber:      "SPALAM" + strconv.Itoa(snInt),
		Model:             "Allez_2020",
		Colour:            "Red",
		Owner:             "",
		PlaceOfProduction: "Factory_USA",
		DateOfProduction:  timeNow,
		BikeStore:         "",
		DateOfWarehousing: "",
		TradeHistory:      tradeHistory,
		Transmission:      snTr,
		Brake:             snBr,
		Wheel:             snWh,
		Price:             1500000}

	roadbikeAsBytes, _ := json.Marshal(roadbike)
	err = ctx.GetStub().PutState(roadbike.SerialNumber, roadbikeAsBytes)
	if err != nil {
		return "", err
	}
	fmt.Println(roadbike.Model + " generated !")
	fmt.Println("S/N : " + roadbike.SerialNumber)
	return roadbike.SerialNumber, nil
}

func (s *SmartContract) purchaseRoadbike(ctx contractapi.TransactionContextInterface) error {
	// specaialized state
	var specialized Specialized
	specializedBytes, _ := ctx.GetStub().GetState("Specialized")
	err := json.Unmarshal(specializedBytes, &specialized)
	if err != nil {
		return fmt.Errorf("Failed to read world state. %s", err.Error())
	}

	// bikestore state
	var bikeStore BikeStore
	bikeStoreBytes, _ := ctx.GetStub().GetState("BikeStore")
	err = json.Unmarshal(bikeStoreBytes, &bikeStore)
	if err != nil {
		return fmt.Errorf("Failed to read world state. %s", err.Error())
	}

	sn := ""
	sn, specialized.BikeStock = pop(specialized.BikeStock)
	bikeStore.BikeStock = append(bikeStore.BikeStock, sn)

	// bikestore state
	var roadbike Roadbike
	roadbikeBytes, _ := ctx.GetStub().GetState(sn)
	err = json.Unmarshal(roadbikeBytes, &roadbike)
	if err != nil {
		return fmt.Errorf("Failed to read world state. %s", err.Error())
	}

	roadbike.BikeStore = bikeStore.StoreName

	roadbikeBytes, _ = json.Marshal(roadbike)
	err = ctx.GetStub().PutState(roadbike.SerialNumber, roadbikeBytes)

	bikeStoreBytes, _ = json.Marshal(bikeStore)
	err = ctx.GetStub().PutState("BikeStore", bikeStoreBytes)

	specializedBytes, _ = json.Marshal(specialized)
	err = ctx.GetStub().PutState("Specialized", specializedBytes)

	return nil
}

func (s *SmartContract) getBikeList(ctx contractapi.TransactionContextInterface) ([]string, error) {
	// bikestore state
	var bikeStore BikeStore
	bikeStoreBytes, _ := ctx.GetStub().GetState("RealBikeShop")
	err := json.Unmarshal(bikeStoreBytes, &bikeStore)
	if err != nil {
		return nil, fmt.Errorf("Failed to read world state. %s", err.Error())
	}

	var bikeBytes []byte
	bikeList := []string{}
	for _, bikeSN := range bikeStore.BikeStock {
		bikeBytes, _ = ctx.GetStub().GetState(bikeSN)
		bikeList = append(bikeList, string(bikeBytes))
	}

	return bikeList, nil
}

func (s *SmartContract) purchaseMyBike(ctx contractapi.TransactionContextInterface, _owner string, _sn string) error {
	timeNow := time.Now().Format("2020-01-01")

	// bikestore state
	var bikeStore BikeStore
	bikeStoreBytes, _ := ctx.GetStub().GetState("RealBikeShop")
	err := json.Unmarshal(bikeStoreBytes, &bikeStore)

	// bike state
	var roadbike Roadbike
	roadbikeBytes, _ := ctx.GetStub().GetState(_sn)
	err = json.Unmarshal(roadbikeBytes, &roadbike)
	if err != nil {
		return fmt.Errorf("Failed to read world state. %s", err.Error())
	}
	roadbike.Owner = _owner
	roadbike.DateOfPurchase = timeNow
	roadbikeBytes, _ = json.Marshal(roadbike)
	ctx.GetStub().PutState(_sn, roadbikeBytes)
	// ctx.GetStub().DelState(_sn)

	arr := []string{}
	for _, bikeSN := range bikeStore.BikeStock {
		if bikeSN != _sn {
			arr = append(arr, bikeSN)
		}
	}
	bikeStore.BikeStock = arr
	bikeStoreBytes, _ = json.Marshal(bikeStore)
	err = ctx.GetStub().PutState("RealBikeShop", bikeStoreBytes)
	if err != nil {
		return fmt.Errorf("Failed to read world state. %s", err.Error())
	}

	return nil
}

// 여기 볼 차례

func (s *SmartContract) changeOwner(ctx contractapi.TransactionContextInterface, _owner1 string, _owner2 string) error {
	// bike state
	var roadbike Roadbike
	roadbikeBytes, _ := ctx.GetStub().GetState(_owner1)
	err := json.Unmarshal(roadbikeBytes, &roadbike)
	if err != nil {
		return fmt.Errorf("Failed to read world state. %s", err.Error())
	}

	roadbike.Owner = _owner2
	roadbikeBytes, _ = json.Marshal(roadbike)
	ctx.GetStub().PutState(_owner2, roadbikeBytes)

	return nil
}

func (s *SmartContract) getBikeInfo(ctx contractapi.TransactionContextInterface, _sn string) (Roadbike, error) {
	// bike state
	var roadbike Roadbike
	roadbikeBytes, _ := ctx.GetStub().GetState(_sn)
	err := json.Unmarshal(roadbikeBytes, &roadbike)
	if err != nil {
		return roadbike, fmt.Errorf("Failed to read world state. %s", err.Error())
	}

	return roadbike, nil
}

func (s *SmartContract) getMyBikeInfo(ctx contractapi.TransactionContextInterface, _owner string) (Roadbike, error) {
	// bike state
	var roadbike Roadbike
	roadbikeBytes, _ := ctx.GetStub().GetState(_owner)
	err := json.Unmarshal(roadbikeBytes, &roadbike)
	if err != nil {
		return roadbike, fmt.Errorf("Failed to read world state. %s", err.Error())
	}

	return roadbike, nil
}

func (s *SmartContract) changePart(ctx contractapi.TransactionContextInterface, _owner string, _part string, _sn string) (Roadbike, error) {
	// bike state
	var roadbike Roadbike
	roadbikeBytes, _ := ctx.GetStub().GetState(_owner)
	err := json.Unmarshal(roadbikeBytes, &roadbike)
	if err != nil {
		return roadbike, fmt.Errorf("Failed to read world state. %s", err.Error())
	}

	switch _part {
	case "transmission":
		roadbike.Transmission = _sn
	case "brake":
		roadbike.Brake = _sn
	case "wheel":
		roadbike.Wheel = _sn
	}

	roadbikeBytes, _ = json.Marshal(roadbike)
	ctx.GetStub().PutState(_owner, roadbikeBytes)

	return roadbike, nil
}

// func (s *SmartContract) makeTrade(ctx contractapi.TransactionContextInterface) {
// 	var trade Trade

// }

func (s *SmartContract) signTrade() {

}

//InitLedger function
func (s *SmartContract) InitLedger(ctx contractapi.TransactionContextInterface) error {
	fmt.Println("SmartContract Init")
	empty := []string{}

	//shimano
	shimano := Shimano{TrStock: empty, BrStock: empty, WhStock: empty, SN: 0}
	shimanoBytes, _ := json.Marshal(shimano)
	_ = ctx.GetStub().PutState("Shimano", shimanoBytes)

	//specialized
	specialized := Specialized{TrStock: empty, BrStock: empty, WhStock: empty, BikeStock: empty, SN: 0}
	specializedBytes, _ := json.Marshal(specialized)
	_ = ctx.GetStub().PutState("Specialized", specializedBytes)

	//bikestore
	bikeStore := BikeStore{StoreName: "RealBikeShop", BikeStock: empty}
	bikeStoreBytes, _ := json.Marshal(bikeStore)
	_ = ctx.GetStub().PutState("RealBikeShop", bikeStoreBytes)

	// bikelist
	bikeInfo := make(map[string]Roadbike)
	bikeInfoBytes, _ := json.Marshal(bikeInfo)
	_ = ctx.GetStub().PutState("BikeInfo", bikeInfoBytes)

	// // tradelist
	// tradeInfo := make(map[string]Trade)
	// tradeInfoBytes, _ := json.Marshal(tradeInfo)
	// _ = ctx.GetStub().PutState("TradeInfo", tradeInfoBytes)

	// tradeList
	tradeList := []Trade{}
	tradeListBytes, _ := json.Marshal(tradeList)
	_ = ctx.GetStub().PutState("TradeList", tradeListBytes)

	return nil
}

func main() {

	chaincode, err := contractapi.NewChaincode(new(SmartContract))

	if err != nil {
		fmt.Printf("Error create roadbike-scm chaincode: %s", err.Error())
		return
	}

	if err := chaincode.Start(); err != nil {
		fmt.Printf("Error starting roadbike-scm chaincode: %s", err.Error())
	}
}
